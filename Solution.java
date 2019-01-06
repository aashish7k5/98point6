import java.util.*;
import java.io.*;
import java.lang.*;

public class Solution
{
  public static List<Integer> get = new ArrayList<Integer>();
  public static boolean win = false;
  public static boolean error = false;
  public static boolean draw = false;
  public static void main(String args[])
  {
    char[][] arr = new char[6][5]; //matrix of the game
    char p = 0; //player identification
    setUpArray(arr);
    int pchoose = 1;

    //Scanner for data input
    Scanner reader = new Scanner(System.in);
    while(!win && !draw)
    {
      error = false;
      String s = reader.nextLine();
      s = s.trim();

      //read PUT input
      if(s.substring(0, 3).equals("PUT"))
      {
        int col = 0;
        if(s.length()!=5)
        {
          error = true; System.out.println("ERROR"); continue; //if PUT input number is invalid number of digits, ERROR
        }
        try{
          col = Integer.parseInt(String.valueOf(s.substring(4))); //extracts col, the column number we're putting player's turn in
        }
        catch(NumberFormatException e)
        {
          error = true; System.out.println("ERROR"); continue; //if PUT input number is invalid, ERROR
        }
        if(col>arr[0].length-1)
        {
          error = true; System.out.println("ERROR"); continue; //if PUT input number is greater than size of matrix allows, ERROR
        }

        //Decides which player is currently playing
        if(pchoose>0) System.out.println(put(col, '1', arr));
        else System.out.println(put(col, '2', arr));

        //If error, then last put transaction never went through, stick to same player. If no error, change players.
        if(!error)
        {
          pchoose = pchoose*-1;
        }
      }
      else if(s.equals("BOARD"))
      {
        board(arr);
      }
      else if(s.equals("GET"))
      {
        for(int i = 0; i<get.size(); i++) System.out.println(get.get(i));
      }
      else
      {
        error = true; System.out.println("ERROR"); continue;
      }
    }
  }

  //Sets up the rudimentary graphics of the matrix, as given by problem specifications
  public static void setUpArray(char[][] arr)
  {
    for(int i = 0; i<arr.length-2; i++)
    {
      arr[i][0] = '|';
    }
    arr[arr.length-2][0] = '+';
    for(int i = 1; i<arr[0].length; i++)
    {
      for(int j = 0; j<arr.length-2; j++)
      {
        arr[j][i] = '0';
      }
    }
    for(int i = 1; i<arr[0].length; i++)
    {
      for(int j = 0; j<arr.length; j++)
      {
        if(j == arr.length-1) arr[j][i] = (char)((i)+'0');
        else if(j == arr.length-2) arr[j][i] = '-';
      }
    }
  }

  //Handles changing the matrix at putting new moves
  public static String put(int col, char p, char[][] arr)
  {
    if(arr[arr.length-2][col] == '0')
      arr[arr.length-2][col] = p;
    else
    {
      error = true;
      for(int i = arr.length-2; i>-1; i--)
      {
        if(arr[i][col] == '0')
        {
          arr[i][col] = p;
          error = false;
          break;
        }
      }
      if(error) return "ERROR";
      else get.add(col);
    }
    if(get.size()==16)
    {
      draw = true;
      return "DRAW";
    }
    if(checkWin(arr)) {win = true; return "WIN";}
    else return "OK";
  }

  //Check if win
  public static boolean checkWin(char[][] arr)
  {
    int count = 0; char s = 0; //Count identical charactera across winning directions, and if enough are reached, its a win.
    //Check if rows won
    for(int i = 0; i<arr.length-2; i++)
    {
      s = arr[i][1];
      count = 0;
      for(int j = 1; j<arr[0].length; j++)
      {
        if(s==arr[i][j] && s!='0') count++;
        if(count==arr[0].length-1) return true;
      }
    }

    //Check if any columns won
    for(int i = 1; i<arr[0].length; i++)
    {
      s = arr[0][i]; count = 0;
      for(int j = 0; j<arr.length-2; j++)
      {
        if(s==arr[j][i] && s!='0') count++;
        if(count==arr[0].length-1) return true;
      }
    }

    //Check if diagonal 1 results in a win
    count = 0;
    for(int i = 0; i<arr.length-3; i++)
    {
      s = arr[0][1];
      if(arr[i][i+1]==arr[i+1][i+2] && arr[i+1][i+2] == s && s!='0') count++;
      else break;
      if(count == arr.length-3) return true;
    }

    //Check if diagonal 2 results in a win
    count = 0;
    for(int i = arr.length-3; i>=1; i--)
    {
      s = arr[arr.length-3][1];
      if(arr[i][arr.length-2-i]==arr[i-1][arr.length-2-i+1] && arr[i-1][arr.length-2-i+1] == s && s!='0') count++;
      else break;
      if(count == arr.length-3) return true;
    }

    //If no condition reports a win, continue with the game.
    return false;
  }

  //Method for printing the board out
  public static void board(char[][] arr)
  {
    for(int i = 0; i<arr.length; i++)
    {
      for(int j = 0; j<arr[0].length; j++)
      {
        System.out.print(arr[i][j]);
      }
      System.out.println();
    }
  }
}
