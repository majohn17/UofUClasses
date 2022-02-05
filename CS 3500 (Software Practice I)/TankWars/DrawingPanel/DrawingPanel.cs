using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Windows.Shapes;
using DrawingPanel.Properties;
using TankModel;
using static TankModel.Constants;
using TankWars;

namespace TankView
{
    public class DrawingPanel : Panel
    {
        /// <summary>
        /// local direction to the world for drawing
        /// </summary>
        private World theWorld;

        /// <summary>
        /// images used for the game
        /// </summary>
        Dictionary<string, Bitmap> picture = new Dictionary<string, Bitmap>();

        /// <summary>
        /// beams that need to be removed
        /// </summary>
        List<Beam> removeBeams = new List<Beam>();
       
        /// <summary>
        /// creates a drawing panel with a world
        /// </summary>
        /// <param name="w"></param>
        public DrawingPanel(World w)
        {
            DoubleBuffered = true;
            theWorld = w;
            picture["wall"] = Resources.WallSprite;
            picture["Backround"] = Resources.Background;
            picture["Heart"] = Resources.Heart;
            picture["Explosion"] = Resources.Explosion;
        }

        /// <summary>
        /// Helper method for DrawObjectWithTransform
        /// </summary>
        /// <param name="size">The world (and image) size</param>
        /// <param name="w">The worldspace coordinate</param>
        /// <returns></returns>
        private static int WorldSpaceToImageSpace(int size, double w)
        {
            return (int)w + size / 2;
        }

        // A delegate for DrawObjectWithTransform
        // Methods matching this delegate can draw whatever they want using e  
        public delegate void ObjectDrawer(object o, PaintEventArgs e);


        /// <summary>
        /// This method performs a translation and rotation to drawn an object in the world.
        /// </summary>
        /// <param name="e">PaintEventArgs to access the graphics (for drawing)</param>
        /// <param name="o">The object to draw</param>
        /// <param name="worldSize">The size of one edge of the world (assuming the world is square)</param>
        /// <param name="worldX">The X coordinate of the object in world space</param>
        /// <param name="worldY">The Y coordinate of the object in world space</param>
        /// <param name="angle">The orientation of the objec, measured in degrees clockwise from "up"</param>
        /// <param name="drawer">The drawer delegate. After the transformation is applied, the delegate is invoked to draw whatever it wants</param>
        private void DrawObjectWithTransform(PaintEventArgs e, object o, int worldSize, double worldX, double worldY, double angle, ObjectDrawer drawer)
        {
            // "push" the current transform
            System.Drawing.Drawing2D.Matrix oldMatrix = e.Graphics.Transform.Clone();

            int x = WorldSpaceToImageSpace(worldSize, worldX);
            int y = WorldSpaceToImageSpace(worldSize, worldY);
            e.Graphics.TranslateTransform(x, y);
            if (!double.IsNaN(angle))
                e.Graphics.RotateTransform((float)angle);
            drawer(o, e);

            // "pop" the transform
            e.Graphics.Transform = oldMatrix;
        }

        /// <summary>
        /// Obtains the correctly colored image files based on id of tank
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        private string GetColor(int id)
        {

            switch (id % 8)
            {
                case 0:
                    {
                        if (!picture.ContainsKey("BlueTank"))
                        {
                            picture["BlueTank"] = Resources.BlueTank;
                            picture["BlueTurret"] = Resources.BlueTurret;
                            picture["BlueShot"] = Resources.BlueShot;
                        }
                        return "Blue";

                    }
                case 1:
                    {
                        if (!picture.ContainsKey("DarkTank"))
                        {
                            picture["DarkTank"] = Resources.DarkTank;
                            picture["DarkTurret"] = Resources.DarkTurret;
                            picture["DarkShot"] = Resources.DarkShot;
                        }
                        return "Dark";
                    }
                case 2:
                    {
                        if (!picture.ContainsKey("GreenTank"))
                        {
                            picture["GreenTank"] = Resources.GreenTank;
                            picture["GreenTurret"] = Resources.GreenTurret;
                            picture["GreenShot"] = Resources.GreenShot;
                        }
                        return "Green";
                    }
                case 3:
                    {
                        if (!picture.ContainsKey("LightGreenTank"))
                        {
                            picture["LightGreenTank"] = Resources.LightGreenTank;
                            picture["LightGreenTurret"] = Resources.LightGreenTurret;
                            picture["LightGreenShot"] = Resources.LightGreenShot;
                        }
                        return "LightGreen";

                    }
                case 4:
                    {
                        if (!picture.ContainsKey("OrangeTank"))
                        {
                            picture["OrangeTank"] = Resources.OrangeTank;
                            picture["OrangeTurret"] = Resources.OrangeTurret;
                            picture["OrangeShot"] = Resources.OrangeShot;
                        }
                        return "Orange";

                    }
                case 5:
                    {
                        if (!picture.ContainsKey("PurpleTank"))
                        {
                            picture["PurpleTank"] = Resources.PurpleTank;
                            picture["PurpleTurret"] = Resources.PurpleTurret;
                            picture["PurpleShot"] = Resources.PurpleShot;
                        }
                        return "Purple";

                    }
                case 6:
                    {
                        if (!picture.ContainsKey("RedTank"))
                        {
                            picture["RedTank"] = Resources.RedTank;
                            picture["RedTurret"] = Resources.RedTurret;
                            picture["RedShot"] = Resources.RedShot;
                        }
                        return "Red";

                    }
                case 7:
                    {
                        if (!picture.ContainsKey("YellowTank"))
                        {
                            picture["YellowTank"] = Resources.YellowTank;
                            picture["YellowTurret"] = Resources.YellowTurret;
                            picture["YellowShot"] = Resources.YellowShot;
                        }
                        return "Yellow";

                    }

            }
            return "Blue";
        }

        /// <summary>
        /// Drawing delegate for Walls
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void WallDrawer(object o, PaintEventArgs e)
        {
            Wall w = o as Wall;
            e.Graphics.DrawImage(picture["wall"], (-wallSize / 2), (-wallSize / 2), wallSize, wallSize);
        }
        /// <summary>
        /// draws in a line of walls, 
        /// </summary>
        /// <param name="e"></param>
        /// <param name="wall"></param>
        /// <param name="worldSize"></param>
        /// <param name="wallDrawer"></param>
        private void WallLineDrawer(PaintEventArgs e, Wall wall, int worldSize, Action<object, PaintEventArgs> wallDrawer)
        {
            double x = wall.GetX();
            double y = wall.GetY();
            double target;
            double goal;

            bool negdirection;
            bool xdirection = x != wall.GetX2();
            if (xdirection)
            {
                goal = wall.GetX2();
                target = x;


            }
            else
            {
                goal = wall.GetY2();
                target = y;

            }

            if (target >= goal)
            {
                double temp = target;
                target = goal;
                goal = temp;
                negdirection = true;
            }
            else
            {
                negdirection = false;
            }
            while (target <= goal)
            {
                DrawObjectWithTransform(e, wall, theWorld.WorldSize, x, y, 0, WallDrawer);
                target += Constants.wallSize;
                if (xdirection)
                {
                    if (negdirection)
                    {
                        x -= Constants.wallSize;
                    }
                    else
                    {
                        x += Constants.wallSize;
                    }
                }
                else
                {
                    if (negdirection)
                    {
                        y -= Constants.wallSize;
                    }
                    else
                    {
                        y += Constants.wallSize;
                    }
                }
            }
        }

        /// <summary>
        /// Drawing delegate for Tanks
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void TankDrawer(object o, PaintEventArgs e)
        {
            Tank t = o as Tank;

            if (t.GetDeathStatus())
            {
                return;
            }
            string color = GetColor(t.GetID());

            e.Graphics.DrawImage(picture[color + "Tank"], -tankSize / 2, -tankSize / 2, tankSize, tankSize);

        }
        /// <summary>
        /// Draws the turret of the tank
        /// </summary>
        /// <param name="o"></param>
        /// <param name="e"></param>
        private void TankTurretDrawer(object o, PaintEventArgs e)
        {
            Tank t = o as Tank;
            string color = GetColor(t.GetID());
            if (t.GetDeathStatus())
            {

                return;
            }


            e.Graphics.DrawImage(picture[color + "Turret"], (-tankSize / 2) + 4.5f, (-tankSize / 2) + 5.25f, turretSize, turretSize);
        }
        /// <summary>
        /// Draws health Bar and the score of the player
        /// </summary>
        /// <param name="o"></param>
        /// <param name="e"></param>
        private void TankDisplayDrawer(object o, PaintEventArgs e)
        {
            Tank t = o as Tank;

            if (t.GetDeathStatus())
            {
                return;
            }
            using (System.Drawing.SolidBrush Brush = new System.Drawing.SolidBrush(System.Drawing.Color.White))
            {
                StringFormat format = new StringFormat();
                Font font = new Font("Serif", 12);
                format.Alignment = StringAlignment.Center;
                e.Graphics.DrawString(t.GetName() + ": " + t.GetScore(), font, Brush, 0, tankSize / 2, format);
            }
            DrawHealth(t, e);
        }
        /// <summary>
        /// draws a heart spaced out above the tank for each HP that the tank has 
        /// </summary>
        /// <param name="t"></param>
        /// <param name="e"></param>
        private void DrawHealth(Tank t, PaintEventArgs e)
        {
            for (int i = 0; i < t.GetHP(); i++)
                e.Graphics.DrawImage(picture["Heart"], -tankSize / 2 + (i * (heartSize + 3)) + 4.5f, -4 * tankSize / 5, heartSize, heartSize);
        }
        /// <summary>
        /// draws the explosion sprite 
        /// </summary>
        /// <param name="o"></param>
        /// <param name="e"></param>
        private void ExplosionDrawer(object o, PaintEventArgs e)
        {
            Explosion t = o as Explosion;
            if (t.Remove())
            {
                
                return;
            }
            e.Graphics.DrawImage(picture["Explosion"], -tankSize / 2 , -4 * tankSize / 5, tankSize, tankSize);
        }

        /// <summary>
        /// Drawing Delegate for Projectiles
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void ProjectileDrawer(object o, PaintEventArgs e)
        {
            Projectile p = o as Projectile;
            string color = GetColor(p.GetOwner());

            e.Graphics.DrawImage(picture[color + "Shot"], (-projectileSize / 2), (-projectileSize / 2), projectileSize, projectileSize);
        }

        /// <summary>
        /// Drawing Delegate for Powerups
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void PowerupDrawer(object o, PaintEventArgs e)
        {
            Powerup p = o as Powerup;

            e.Graphics.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
            using (System.Drawing.SolidBrush yellowBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Yellow))
            using (System.Drawing.SolidBrush greenBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Green))
            {
                // Circles are drawn starting from the top-left corner.
                // So if we want the circle centered on the powerup's location, we have to offset it
                // by half its size to the left (-width/2) and up (-height/2)
                RectangleF r = new RectangleF((-powerupSize / 2), (-powerupSize / 2), Constants.powerupSize, Constants.powerupSize);
                e.Graphics.FillEllipse(yellowBrush, r);

                int innerCircle = 2 * powerupSize / 3;
                r = new RectangleF((-innerCircle / 2), (-innerCircle / 2), innerCircle, innerCircle);
                e.Graphics.FillEllipse(greenBrush, r);
            }
        }

        /// <summary>
        /// Drawing Delegate for Beams
        /// </summary>
        /// <param name="o">The object to draw</param>
        /// <param name="e">The PaintEventArgs to access the graphics</param>
        private void BeamDrawer(object o, PaintEventArgs e)
        {

            Beam b = o as Beam;

            if (b.Remove())
            {

                removeBeams.Add(b);


                return;
            }

            e.Graphics.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
            String colorName = GetColor(b.GetOwner());
            if (colorName.Equals("Dark"))
            {
                colorName = "DarkViolet";
            }
            Color tankColor = Color.FromName(colorName);
            using (Pen Whiteline = new Pen(System.Drawing.Color.White, 4f))
            using (Pen Colorline = new Pen(tankColor, 2f))
            {
                e.Graphics.DrawLine(Whiteline, 0, 0, 0, -2000);
                e.Graphics.DrawLine(Colorline, 0, 0, 0, -2000);
            }
        }

        
        /// <summary>
        /// This method is invoked when the DrawingPanel needs to be re-drawn
        /// </summary>
        /// <param name="e"></param>
        protected override void OnPaint(PaintEventArgs e)
        {
            lock (theWorld)
            {
                if (theWorld.Tanks.Count > 0)
                {
                    // Transform World to Draw 
                    double playerX = theWorld.Tanks[theWorld.PlayerID].GetX();
                    double playerY = theWorld.Tanks[theWorld.PlayerID].GetY();
                    // Calculate view/world size ratio
                    double ratio = (double)viewSize / (double)theWorld.WorldSize;
                    int halfSizeScaled = (int)(theWorld.WorldSize / 2.0 * ratio);
                    // Calculate translated coordinates
                    double inverseTranslateX = -WorldSpaceToImageSpace(theWorld.WorldSize, playerX) + halfSizeScaled;
                    double inverseTranslateY = -WorldSpaceToImageSpace(theWorld.WorldSize, playerY) + halfSizeScaled;
                    e.Graphics.TranslateTransform((float)inverseTranslateX, (float)inverseTranslateY);
                }

                // Draw Background
                e.Graphics.DrawImage(picture["Backround"], 0, 0, theWorld.WorldSize, theWorld.WorldSize);

                // Draw the walls
                foreach (Wall wall in theWorld.Walls.Values)
                {
                    WallLineDrawer(e, wall, theWorld.WorldSize, WallDrawer);
                    //  DrawObjectWithTransform(e, wall, theWorld.WorldSize, wall.GetX(), wall.GetY(), 0, WallDrawer);
                }


                // Draw the players and explosions
                foreach (Tank tank in theWorld.Tanks.Values)
                {
                    if (tank.GetHP() == 0)
                    {
                        //checks if there needs to be an explosion object to be drawn and if there is not, it makes one 
                        if (!theWorld.Explosions.ContainsKey(tank.GetID()))
                        theWorld.tankDied(tank);
                        continue;
                    }
                    if (theWorld.Explosions.ContainsKey(tank.GetID()))
                    {
                        theWorld.Explosions.Remove(tank.GetID());
                    }

                    DrawObjectWithTransform(e, tank, theWorld.WorldSize, tank.GetX(), tank.GetY(), tank.GetAngle(), TankDrawer);
                    DrawObjectWithTransform(e, tank, theWorld.WorldSize, tank.GetX(), tank.GetY(), tank.GetTurretAngle(), TankTurretDrawer);
                    DrawObjectWithTransform(e, tank, theWorld.WorldSize, tank.GetX(), tank.GetY(), 0, TankDisplayDrawer);
                }
                foreach (Explosion explosion in theWorld.Explosions.Values)
                {
                    DrawObjectWithTransform(e, explosion, theWorld.WorldSize, explosion.GetX(), explosion.GetY(), 0, ExplosionDrawer);
                }
             
                // Draw the projectiles
                foreach (Projectile proj in theWorld.Projectiles.Values)
                {
                    DrawObjectWithTransform(e, proj, theWorld.WorldSize, proj.GetX(), proj.GetY(), proj.GetAngle(), ProjectileDrawer);
                }

                // Draw the powerups
                foreach (Powerup power in theWorld.Powerups.Values)
                {
                    DrawObjectWithTransform(e, power, theWorld.WorldSize, power.GetX(), power.GetY(), 0, PowerupDrawer);
                }

                // Draw the beams

                foreach (Beam beam in theWorld.Beams.Values)
                {
                    DrawObjectWithTransform(e, beam, theWorld.WorldSize, beam.GetX(), beam.GetY(), beam.GetDirection().ToAngle(), BeamDrawer);
                }
                // removes dead beams
                foreach (Beam beam in removeBeams)
                {
                    theWorld.Beams.Remove(beam.GetID());

                }
                removeBeams.Clear();

            }

            // Do anything that Panel (from which we inherit) needs to do
            base.OnPaint(e);
        }


    }
}

