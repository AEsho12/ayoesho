package ac.liv.csc.comp201.control;

import ac.liv.csc.comp201.model.IMachine;
import java.util.Scanner;

public class CoinHandlerManager {

    /**
     * This function manage the coins enter by the user
     * when ever user enter the coin the worth of that coin is add 
     * in to the machine
     * @param machine 
     */
    public void handleCoinsIn(IMachine machine) {
        String code = machine.getCoinHandler().getCoinKeyCode();
        System.out.println("Code is " + code);
        if (code != null) {
            machine.setBalance(machine.getBalance() + Coin.getCoinFromCode(code).getCoinWorth());
        }

    }

    /**
     * Pay's the current balance in coins if it can be paid, this is a best try
     * function if not enough coins then it pays the best it can do
     */
    public void returnChange(IMachine machine) {
        machine.getDisplay().setTextString("Trying to pay change back");
        System.out.println("PAYING CHANGE!!!! balance is " + machine.getBalance());
        Coin[] availableCoins = Coin.getAllcoins();
        for (int i = 0; i < availableCoins.length; i++) {
            for (int j = 0; j < availableCoins.length - 1; j++) {
                if (availableCoins[j + 1].compareTo(availableCoins[j]) == -1) {
                    Coin temp = availableCoins[j];
                    availableCoins[j] = availableCoins[j + 1];
                    availableCoins[j + 1] = temp;
                }
            }
        }

        int currentCoinIndex = 0;
        while (machine.getBalance() != 0 && currentCoinIndex < availableCoins.length) {
            if (machine.getCoinHandler().coinAvailable(availableCoins[currentCoinIndex].getCoinCode())) {
                if (availableCoins[currentCoinIndex].getCoinWorth() > machine.getBalance()) {
                    currentCoinIndex++;
                } else {
                    machine.getCoinHandler().dispenseCoin(availableCoins[currentCoinIndex].getCoinCode());
                    machine.setBalance(machine.getBalance() - availableCoins[currentCoinIndex].getCoinWorth());
                }
            } else {
                currentCoinIndex++;
            }
        }
        System.out.println("PAYING CHANGE!!!! balance is " + machine.getBalance());
    }

}
