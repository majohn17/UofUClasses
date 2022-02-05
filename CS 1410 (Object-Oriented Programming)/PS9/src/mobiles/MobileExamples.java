package mobiles;

import javax.swing.SwingUtilities;

public class MobileExamples
{
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater( () -> new MobileViewer(makeMobile3(), "Mobile 1"));
    }

    public static Mobile makeMobile1 ()
    {
        Mobile m6 = new Mobile(28, 7, new Mobile(10), new Mobile(40));
        Mobile m5 = new Mobile(12, 18, new Mobile(3), new Mobile(2));
        Mobile m4 = new Mobile(5, 20, new Mobile(20), m5);
        Mobile m3 = new Mobile(24, 12, m4, new Mobile(50));
        Mobile m2 = new Mobile(30, 10, new Mobile(25), m3);
        Mobile m1 = new Mobile(20, 40, m2, m6);
        return m1;
    }

    public static Mobile makeMobile2 ()
    {
        Mobile m3 = new Mobile(15, 15, new Mobile(25), new Mobile(25));
        Mobile m2 = new Mobile(15, 15, new Mobile(25), new Mobile(26));
        Mobile m1 = new Mobile(20, 20, m2, m3);
        return m1;
    }

    public static Mobile makeMobile3 ()
    {
        Mobile m4 = new Mobile(15, 10, new Mobile(10), new Mobile(16));
        Mobile m3 = new Mobile(15, 15, new Mobile(26), new Mobile(26));
        Mobile m2 = new Mobile(10, 10, new Mobile(26), m4);
        Mobile m1 = new Mobile(25, 25, m2, m3);
        return m1;
    }
}
