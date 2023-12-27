import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static void adjacencyFinder(char[] input,int[][] adjacency,String[] conflicts,int num){
        int index1=-1,index2=-1;
        char conflict1,conflict2;
        for(int i = 0;i<num;i++)
        {   index1=-1;
            index2=-1;
            conflict1=conflicts[i].charAt(0);
            conflict2=conflicts[i].charAt(2);
            for(int j = 0;j<input.length;j++)
            {
                if(conflict1==input[j])
                    index1=j;
                if(conflict2==input[j])
                    index2=j;
                if(index1!=-1 && index2!=-1)
                    break;
            }
            adjacency[index1][index2]=1;
            adjacency[index2][index1]=1;

        }
    }
    static void applyGreedy(int[] colored,int[][] adjacency){
        int[] available = new int[colored.length];
        colored[0]=0;
        for(int i = 1;i<colored.length;i++)
        {
            Arrays.fill(available,0);
            for(int j = 0;j<i;j++)
            {
                if(adjacency[i][j]==1)
                {
                    available[colored[j]]=1;
                }
            }
            for(int k = 0;k< colored.length;k++)
            {
                if(available[k]==0){
                    colored[i]=k;
                    break;
                }
            }
        }


    }




    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Please enter comma-separated vertices");
            String input = sc.nextLine();


            String[] charStrings = input.split(",");

            char[] inputarray = new char[charStrings.length];

            // Extract characters from each substring and store in the char array
            for (int i = 0; i < charStrings.length; i++) {
                // Assuming each substring is a single character
                inputarray[i] = charStrings[i].charAt(0);
            }
            System.out.println("Enter the number of conflicts");
            int num = sc.nextInt();
            int[][] adjacency = new int[charStrings.length][charStrings.length];
            sc.nextLine();
            String[] conflicts = new String[num];
            System.out.println("Enter each conflict one after another in that form 'a-b'.");
            for(int i = 0;i<num;i++)
            {
                conflicts[i]=sc.nextLine();
            }
            adjacencyFinder(inputarray,adjacency,conflicts,num);

            String[] colors = {"Blue","Red","Green","Yellow","Orange","Purple","Black","White","Gray","Cyan"};
            int[] colored = new int[charStrings.length];
            for(int i = 0;i< charStrings.length;i++)
            {
                colored[i]=-1;
            }
            applyGreedy(colored,adjacency);
            for(int i = 0;i< charStrings.length;i++)
            {
                System.out.println(inputarray[i]+" - "+colors[colored[i]]);
            }


        }
        catch (Exception e){
            System.out.println("Error");
            System.exit(2);
        }

    }
}