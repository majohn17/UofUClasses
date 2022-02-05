// Created by Isaac Gibson (u1173413) and Matthew Johnsen (u1173601)

using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TankModel;
using TankWars;

namespace Server
{
    public class DataBaseHandling
    {
        /// <summary>
        /// string used to connect to the database
        /// </summary>
        public const string connectionString = "server=atr.eng.utah.edu;database=cs3500_u1173601;uid=cs3500_u1173601;password=1231234";

        /// <summary>
        /// creates a list of playerModels
        /// </summary>
        /// <param name="tanks"></param>
        /// <returns></returns>
        private static List<PlayerModel> PlayerData(Dictionary<int,Tank> tanks)
        {
            List<PlayerModel> players = new List<PlayerModel>();
            foreach (Tank t in tanks.Values)
            {
                uint.TryParse("" + t.GetScore(), out uint score);
                players.Add(new PlayerModel(t.GetName(), score, t.accuracy));
            }
            
            return players;
        }

        /// <summary>
        /// creates a list of player data from the data base of a specific game
        /// </summary>
        /// <param name="GameID"></param>
        /// <returns></returns>
        private static List<PlayerModel> PlayerDataFromDatabase(uint GameID)
        {
            List<PlayerModel> players = new List<PlayerModel>();
            using (MySqlConnection conn = new MySqlConnection(connectionString))
            {
                try
                {
                    conn.Open();

                    // Create a command
                    MySqlCommand command = conn.CreateCommand();
                    command.CommandText = "Select * from GamesPlayed where GamesPlayed.GameID = " + GameID + ";";
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            players.Add(new PlayerModel((string)reader["PlayerName"], (uint)reader["Score"], (uint)reader["Accuracy"]));
                           
                        }
                    }
                    
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                }
                finally
                {
                    conn.Close();
                }
            }
            return players;

        }

        /// <summary>
        /// Creates a game model of a Game from the data base given the ID
        /// </summary>
        /// <param name="GameID"></param>
        /// <param name="players"></param>
        /// <returns></returns>
        private static GameModel CreateModel(uint GameID)
        {
            uint duration = 0;
            List<PlayerModel> players = PlayerDataFromDatabase(GameID);

            using (MySqlConnection conn = new MySqlConnection(connectionString))
            {
                try
                {
                    conn.Open();
                    
                    // Create a command
                    MySqlCommand command = conn.CreateCommand();
                    command.CommandText = "Select * from Games where Games.GameID = " + GameID + ";";
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        reader.Read();
                       duration = (uint)reader["Duration"];
                        
                    }
                    
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                }
                finally
                {
                    conn.Close();
                }
            }
            return CreateModel(GameID, duration, players);
        }


        /// <summary>
        /// Creates a game model of a Game from the data base given the ID
        /// </summary>
        /// <param name="id"></param>
        /// <param name="duration"></param>
        /// <param name="players"></param>
        /// <returns></returns>
        private static GameModel CreateModel(uint id,uint duration,List<PlayerModel> players)
        {
            GameModel game = new GameModel(id,duration);
            foreach(PlayerModel player in players)
            {
                game.AddPlayer(player.Name,player.Score,player.Accuracy);
            }
            return game;

        }

        /// <summary>
        /// Returns a dictionary of all games stored in the database
        /// </summary>
        /// <returns></returns>
        public static Dictionary<uint, GameModel> AllGames()
        {
            Dictionary<uint, GameModel> games = new Dictionary<uint, GameModel>();
            //how many games are there
           
           foreach(uint i in GamesStoredinDatabase())
            {
               games.Add( i,CreateModel(i));
            }

            
            return games;
        }
        /// <summary>
        /// returns a list of the gameID of all games stored in the dataBase
        /// </summary>
        /// <returns></returns>
        private static List<uint> GamesStoredinDatabase()
        {
            List<uint> gameIDs = new List<uint>();
            using (MySqlConnection conn = new MySqlConnection(connectionString))
            {
                try
                {
                    conn.Open();

                    // Create a command
                    MySqlCommand command = conn.CreateCommand();
                    command.CommandText = "Select GameID from Games natural join GamesPlayed;";
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while(reader.Read())
                        {
                            if(!gameIDs.Contains((uint)reader["GameID"] ))
                            gameIDs.Add((uint)reader["GameID"]);
                        }
                       

                    }

                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                }
                finally
                {
                    conn.Close();
                }
            }
            return gameIDs;
        }

        /// <summary>
        /// Creates a list of all the game statistics that were from a specific player
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        public static List<SessionModel> createSessionModel(string name)
        {
            uint GameID = 0, durration = 0, score = 0 , acc = 0;
            List<SessionModel> games = new List<SessionModel>();
            using (MySqlConnection conn = new MySqlConnection(connectionString))
            {
                try
                {
                    conn.Open();

                    // Create a command
                    MySqlCommand command = conn.CreateCommand();
                    command.CommandText = "Select * from Games natural join GamesPlayed where PlayerName = '" + name + "';";
                    using (MySqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            GameID = (uint)reader["GameID"];
                            durration = (uint)reader["Duration"];
                            score = (uint)reader["Score"];
                            acc = (uint)reader["Accuracy"];
                            games.Add(new SessionModel(GameID, durration, score, acc));
                        }
                       


                    }

                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                }
                finally
                {
                    conn.Close();
                }
                return games;
            }

        }

        /// <summary>
        /// Stores the current game in the database
        /// </summary>
        /// <param name="theWorld"></param>
        /// <param name="duration"></param>
        public static void SendGameToDatabase(World theWorld, int duration)
        {
            using (MySqlConnection connection = new MySqlConnection(connectionString))
            {
                try
                {
                    var command = connection.CreateCommand();
                    // Inserts the duration to a auto incremeted GameID in the database
                    command.CommandText = "insert into Games (Duration) values (" + duration + ")";
                    connection.Open();
                    command.ExecuteNonQuery();

                    // Obtains the new Unique ID that was created for this specific game
                    uint gameid = (uint)command.LastInsertedId;

                    foreach (Tank t in theWorld.Tanks.Values)
                    {
                        command.CommandText = "insert into GamesPlayed (GameID,PlayerName,Score,Accuracy) values " +
                            "(" + gameid + ",'" + t.GetName() + "'," + t.GetScore() + "," + t.accuracy + ")";
                        command.ExecuteNonQuery();
                    }
                }
                catch (Exception e)
                {

                }
                finally
                {
                    connection.Close();
                }
            }
        }
    }
}
