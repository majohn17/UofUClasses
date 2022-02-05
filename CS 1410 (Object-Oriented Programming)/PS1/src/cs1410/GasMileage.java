package cs1410;

import cs1410lib.Dialogs;

/**
 * The purpose of this class is to take data from the user relating to a car and compute different gas mileage
 * statistics related to it.
 * 
 * @author Matthew Johnsen
 *
 */
public class GasMileage
{
    /**
     * The main method is required for the program to run and is where the helper method is called to compute the car
     * info.
     * 
     */
    public static void main (String[] args)
    {
        displayCarInfo();
    }

    /**
     * This helper method takes the info from the user and converts it to the information that is displayed.
     */
    public static void displayCarInfo ()
    {
        String tempString;
        String car = Dialogs.showInputDialog("Name a type of car.");
        tempString = Dialogs.showInputDialog("How many miles have you driven since last filling up your gas?");
        int milesSinceGas = Integer.parseInt(tempString);
        tempString = Dialogs.showInputDialog("How much does a gallon of gas cost?");
        double gallonGasCost = Double.parseDouble(tempString);
        tempString = Dialogs.showInputDialog("How many gallons will it take to fill you gas tank?");
        double numFillGallons = Double.parseDouble(tempString);
        double totalGasCost = gallonGasCost * numFillGallons;

        System.out.print(car + "\nCost to fill tank: $" + (String.format("%.2f", totalGasCost))
                + "\nMiles per gallon since last fill-up: " + (String.format("%.2f", milesSinceGas / numFillGallons))
                + "\nGas cost per mile since last fill-up: $" + (String.format("%.2f", totalGasCost / milesSinceGas)));
        Dialogs.showMessageDialog(car + "\nCost to fill tank: $" + (String.format("%.2f", totalGasCost))
                + "\nMiles per gallon since last fill-up: " + (String.format("%.2f", milesSinceGas / numFillGallons))
                + "\nGas cost per mile since last fill-up: $" + (String.format("%.2f", totalGasCost / milesSinceGas)));
    }
}
