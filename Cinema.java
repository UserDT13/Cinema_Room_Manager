import java.util.Scanner;

class Cinema {
    public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      int inputOptions = -1;
      int purchasedTickets = 0;
      int[] income = new int[] {0, 0};
      int currentIncome = 0;
      boolean validInput;
      
      System.out.println("Enter the number of rows:");
      int numRows = scanner.nextInt();
      System.out.println("Enter the number of seats in each row:");
      int numSeats = scanner.nextInt();

      char[][] cinemaArr = new char[numRows][numSeats];

      for (int r = 0; r < cinemaArr.length; r++) {
        for (int c = 0; c < cinemaArr[r].length; c++) {
          cinemaArr[r][c] = 'S';
        }
      }

      income = getTicketPrice(numRows, numSeats, -1);

      while (inputOptions != 0) {
        printOptions();

        inputOptions = scanner.nextInt();

        switch (inputOptions) {
          case 1:
            printSeatingArrangement(numSeats, cinemaArr);
            break;
          case 2:
            validInput = false;
            while (!validInput) {
              System.out.println("Enter a row number:");
              int row = scanner.nextInt();
              System.out.println("Enter a seat number in that row:");
              int seat = scanner.nextInt();
              validInput = reserveSeat(row, seat, cinemaArr);

              if (validInput) {
                income = getTicketPrice(numRows, numSeats, row);
                System.out.printf("Ticket price: $%d\n", income[0]);
                currentIncome += income[0];
                purchasedTickets++;
              }
            }
            break;
          case 3:
            showStatistics(purchasedTickets, numRows, numSeats, currentIncome, income[1]);
        }
      }
      scanner.close();
    }

    public static void printSeatingArrangement(int numSeats, char[][] cinemaArr) {
      System.out.print("Cinema:\n ");

      for (int i = 0; i < numSeats; i++) {
        int column = i + 1;
        System.out.print(" " + column);
      }

      for (int r = 0; r < cinemaArr.length; r++) {
        int row = r + 1;
        System.out.print("\n" + row + " ");
        for (int c = 0; c < cinemaArr[r].length; c++) {
          System.out.print(cinemaArr[r][c] + " ");
        }
      }
    }

    public static void printOptions() {
      System.out.println("""
        
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
          """);
    }

    public static boolean reserveSeat(int row, int seat,char[][] cinemaArr) {
      if (row <= 0 || seat <= 0 || row > cinemaArr.length || seat > cinemaArr[row -1].length) {
        System.out.println("Wrong input!");
        return false;
      } else if (cinemaArr[row - 1][seat - 1] == 'B') {
        System.out.println("That ticket has already been purchased!");
        return false;
      } else {
        cinemaArr[row - 1][seat - 1] = 'B';
        return true;
      }
    }

    public static int[] getTicketPrice(int numRows, int numSeats, int row) {
      int result[] = new int[] {0, 0};
      
      if (numRows * numSeats <= 60) {
        result[0] = 10;
        result[1] = numRows * numSeats * 10;
      } else {
        int frontSeats = numRows / 2;
        int backSeats = numRows - frontSeats;
        if (row > 0) {
          result[0] = row <= frontSeats ? 10 : 8;
        }
        result[1] = frontSeats * numSeats * 10 + backSeats * numSeats * 8;
      }
      return result;
    }

    public static void showStatistics(int purchasedTickets, int numRows, int numSeats, int currentIncome, int totalIncome) {
      double percentage = 100.0 * purchasedTickets / (numRows * numSeats);

      System.out.println("Number of purchased tickets: " + purchasedTickets);
      System.out.printf("Percentage: %.2f%%\n", percentage);
      System.out.printf("Current income: $%d\n", currentIncome);
      System.out.printf("Total income: $%d\n", totalIncome);

    }
}
