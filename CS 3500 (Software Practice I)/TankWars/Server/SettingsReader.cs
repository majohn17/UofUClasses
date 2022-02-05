using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using TankModel;

namespace Server
{
    public class SettingsReader
    {
        public int worldSize { get; private set; }
        public int MSPerFrame { get; private set; }
        public int FramesPerShot { get; private set; }
        public int RespawnRate { get; private set; }

        public int MaxHitPoints { get; private set; }
        public double ProjectileSpeed { get; private set; }
        public double TankSpeed { get; private set; }
        public int MaxPowerups{ get; private set; }
        public int MaxPowerupDelay { get; private set; }
        public int TankSize = 60;
        public int WallSize = 50;
        public int PowerupSize = 30;
        public List<String> walls { get; private set; }

        public SettingsReader()
        {
            walls = new List<string>();
            xmlRead();
        }
        
        /// <summary>
        /// Goes through the settings and determines each of the settings for the server
        /// </summary>
        public void xmlRead()
        {
            using (XmlReader reader = XmlReader.Create("..\\..\\..\\Resources\\GameSettings.xml"))
            {
                
                while (reader.Read())
                {
                    if (reader.IsStartElement())
                    {
                        switch (reader.Name)
                        {

                            case "UniverseSize":
                                {
                                    reader.Read();
                                    int.TryParse(reader.Value, out int temp);
                                    worldSize = temp;
                                    break;
                                }
                            case "MSPerFrame":
                                {
                                    reader.Read();
                                    int.TryParse(reader.Value, out int temp);
                                    MSPerFrame = temp;
                                    break;
                                }
                            case "FramesPerShot":
                                {
                                    reader.Read();
                                    int.TryParse(reader.Value, out int temp);
                                    FramesPerShot = temp;
                                    break;
                                }
                            case "RespawnRate":
                                {
                                    reader.Read();
                                    int.TryParse(reader.Value, out int temp);
                                    RespawnRate = temp;
                                    break;
                                }
                            

                            case "MaxHitPoints":
                                {
                                    reader.Read();
                                    int.TryParse(reader.Value, out int temp);
                                    MaxHitPoints = temp;
                                    break;
                                }
                            case "ProjectileSpeed":
                                {
                                    reader.Read();
                                    int.TryParse(reader.Value, out int temp);
                                    ProjectileSpeed = temp;
                                    break;
                                }
                            case "TankSpeed":
                                {
                                    reader.Read();
                                    double.TryParse(reader.Value, out double temp);
                                    TankSpeed = temp;
                                    break;
                                }
                           
                            case "MaxPowerups":
                                {
                                    reader.Read();
                                    int.TryParse(reader.Value, out int temp);
                                    MaxPowerups = temp;
                                    break;
                                }
                            case "MaxPowerupDelay":
                                {
                                    reader.Read();
                                    int.TryParse(reader.Value, out int temp);
                                    MaxPowerupDelay = temp;
                                    break;
                                }
                            case "Wall":
                                {
                                    //reads the first point of the wall
                                    reader.ReadToFollowing("p1");

                                    reader.ReadToFollowing("x");
                                    reader.Read();
                                    double.TryParse(reader.Value,out double x1);

                                    reader.ReadToFollowing("y");
                                    reader.Read();
                                    double.TryParse(reader.Value, out double y1);

                                    reader.ReadToFollowing("p2");

                                    reader.ReadToFollowing("x");
                                    reader.Read();
                                    double.TryParse(reader.Value, out double x2);

                                    reader.ReadToFollowing("y");
                                    reader.Read();
                                    double.TryParse(reader.Value, out double y2);

                                    StringBuilder wall = new StringBuilder();
                                    wall.Append("{\"wall\":");
                                    wall.Append(walls.Count);
                                    wall.Append(",\"p1\":{\"x\":");
                                    wall.Append(x1);
                                    wall.Append(",\"y\":");
                                    wall.Append(y1);
                                    wall.Append("},\"p2\":{\"x\":");
                                    wall.Append(x2);
                                    wall.Append(",\"y\":");
                                    wall.Append(y2);
                                    wall.Append("}}");

                                    walls.Add(wall.ToString());
                                    
                                    break;
                                }
                        }
                    }
                }

            }
        }
    }
}
