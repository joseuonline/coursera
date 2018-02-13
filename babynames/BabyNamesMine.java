
/**
 * Write a description of sf here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyNamesMine {

    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int totalBNames = 0;
        int totalGNames = 0;        
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                totalBNames += 1;
            }
            else {
                totalGirls += numBorn;
                totalGNames += 1;               
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("number of female briths = " + totalGirls);
        System.out.println("number of male briths = " + totalBoys);
        System.out.println("number of boys names = " + totalBNames);        
        System.out.println("number of girls names = " + totalBNames);               
    }

    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource("data/yob1900.csv");
        totalBirths(fr);
    }
    

    // This method returns the rank of the name in the file for the given gender 
     public int getRank(int year, String name, String gender) {
        int rank = -1;
        int count = 1;
        
        //FileResource fr = new FileResource("data/yob" + Integer.toString(year) + ".csv");
        FileResource fr = new FileResource("data/yob" + Integer.toString(year) + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            
            if (rec.get(1).equals(gender) && rec.get(0).equals(name)) {
                rank = count;
            }
            if(rec.get(1).equals(gender)) {
                count++;
            }
            
        }
        //System.out.println(rank);
        System.out.println(rank);
        return rank;
    }

    public void TEST_getRank(){
        getRank(1971, "Frank", "M");
    }    
    
    // This method returns the name of the person in the file at this rank
    public String getName(int year, int rank, String gender){
        String name = null;
        int count = 0;
        
        FileResource fr = new FileResource("data/yob" + Integer.toString(year) + ".csv");
        for (CSVRecord rec : fr.getCSVParser(false)) {
            
            if(rec.get(1).equals(gender)) {
                count++;
            }            
            
            if (count == rank){
                name = rec.get(0);
            }
            
        }
        System.out.println(name);
        return name;
        
    }
    
    //This method determines what name would have been named if they were born in a different year, 
    //based on the same popularity
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank, gender);
        System.out.println(name + " in " + year + " would be " + newName + " in " + newYear);
    }
    
    public void TEST_whatIsNameInYear(){
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    public void TEST_getName(){
        getName(1982, 450, "M");
    }
    
    
    //This method selects a range of files to process and returns an integer, 
    // the year with the highest rank for the name and gender.
    public int yearOfHighestRank(String name, String gender){
        int highYear = -1;
        DirectoryResource dr = new DirectoryResource();

        int topRank = 0;
        int hRank = 0;
        for (File f : dr.selectedFiles()){
            //code
            File actualFile = null;
            FileResource fr = new FileResource(f);
            //CSVRecord cYear = null;
            //for (CSVRecord cYear : fr.getCSVParser()){
                // we get the year
                int year = Integer.parseInt((f.getName()).replaceAll("[^0-9]", ""));
                hRank = getRank(year, name, gender);
              

                if (hRank != -1){
                    //
                    //if (topRank == 0 || topRank == hRank){
                    if (topRank == 0){                        
                        topRank = hRank;
                        actualFile = f;
                        highYear = Integer.parseInt((actualFile.getName()).replaceAll("[^0-9]", ""));
                    }
                    
                    else {
                        if (topRank > hRank){
                            topRank = hRank;
                            actualFile = f;
                            highYear = Integer.parseInt((actualFile.getName()).replaceAll("[^0-9]", ""));
                        }
                        
                    }
                    
                    }

                
                System.out.println("FINAL hRANK " + hRank); 
                System.out.println("FINAL TOPRANK " + topRank); 
              
        }
                
        System.out.println("High year " + highYear);
        return highYear;
    }
    
    
    public void TEST_yearOfHighestRank(){
        String n = "Mich";
        String g = "M";
        yearOfHighestRank(n, g);
    }
    
    //returns a double representing the average rank of the name and gender over the selected files.
    public double getAverageRank(String name, String gender){
        double avgRank = 0;
        DirectoryResource dr = new DirectoryResource();
        int hRank = 0;
        double totalRank = 0;
        double totalNames = 0;
        for (File f : dr.selectedFiles()){

            File actualFile = null;
            FileResource fr = new FileResource(f);
            int year = Integer.parseInt((f.getName()).replaceAll("[^0-9]", ""));
            hRank = getRank(year, name, gender);

            if (hRank != -1){
                //
                totalNames = totalNames + 1;
                totalRank = hRank + totalRank;
                
                }
            
            
            System.out.println("FINAL hRANK " + hRank); 
            System.out.println("FINAL total Rank " + totalRank); 
            System.out.println("FINAL total names " + totalNames); 
              
        }
        avgRank = totalRank / totalNames;        
        System.out.println("AVG Rank " + avgRank);
        return avgRank;
    }
    
        public void TEST_getAverageRank(){
        String n = "Robert";
        String g = "M";
        getAverageRank(n, g);
    }
    
    
    //total number of births of those names with the same gender and same year who are ranked higher than name. 
    public int getTotalBirthsRankedHigher(String name, String gender){
        int numHigherNames = 0;
        DirectoryResource dr = new DirectoryResource();
        int hRank = 0;
        int totalRank = 0;
        double totalNames = 0;
        for (File f : dr.selectedFiles()){

            File actualFile = null;
            FileResource fr = new FileResource(f);
            int year = Integer.parseInt((f.getName()).replaceAll("[^0-9]", ""));
            hRank = getRank(year, name, gender);

            if (hRank != -1){
                //
                numHigherNames = hRank -1;
                
                }
            
            
            System.out.println("FINAL hRANK " + hRank); 
            System.out.println("total above " + numHigherNames); 
            //System.out.println("FINAL total names " + totalNames); 
              
        }
        //avgRank = totalRank / totalNames;        
        //System.out.println("AVG Rank " + avgRank);
        return numHigherNames;
    }
    
        public void TEST_getTotalBirthsRankedHigher(){
        String n = "Emily";
        String g = "F";
        getTotalBirthsRankedHigher(n, g);
    }
}

// 0. find the year file
// 1. rank all names
// 2. find the name
// 3. notice their rank and the gender.
