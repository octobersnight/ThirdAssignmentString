import edu.duke.*;
import java.io.*;
public class Part3{
    public void processGenes(StorageResource sr) {
        int greaterThan60Count = 0;
        System.out.println("Genes with more than 60 characters: ");
        for ( String s : sr.data() )    {
            if (s.length() > 60) {
                //System.out.println(s);
                greaterThan60Count = greaterThan60Count + 1;
            }
        }

        System.out.println("Number of genes that are longer than 60 characters: " + greaterThan60Count);

        int cgRatioGreaterThan035 = 0;
        System.out.println("Genes with a CG Ratio higher than 0.35: ");
        for ( String s : sr.data() ) {
            if (cgRatio(s) > 0.35) {
                //System.out.println(s);
                cgRatioGreaterThan035 = cgRatioGreaterThan035 + 1;
            }
        }

        System.out.println("Number of genes with a CG Ratio higher than 0.35: " + cgRatioGreaterThan035);

        int longestStringLength = 0;
        for ( String s : sr.data() ) {
            if (s.length() > longestStringLength) {
                longestStringLength = s.length();
            }
        }
        System.out.println("Length of longest gene: " + longestStringLength);
    }

    public void testProcessGenes2() {
        FileResource fr = new FileResource("/home/kazuki/IdeaProjects/ThirdAssignmentString/GRch38dnapart.fa");
        String dna = fr.asString();
        String testDNA1 = dna.toUpperCase();
        //printAllGenes(testDNA1);
        System.out.println("Test case 1: " + testDNA1);
        StorageResource geneList1 = getAllGenes(testDNA1);
        processGenes(geneList1);
        System.out.println();
        System.out.println("CTG Count: " + countCTG(testDNA1));
    }

    public int countCTG(String dna) {
        int startIndex = 0;
        int count = 0;
        int index = dna.indexOf("CTG", startIndex);

        while(true) {
            if(index == -1 || count > dna.length()) {
                break;
            }

            count++;
            index = dna.indexOf("CTG", index+3);
        }
        return count;
    }

    public double cgRatio (String dna)  {
        double cgRatio = 0.0;
        int index = 0;
        double cAndGCount = 0;
        while (index < dna.length())   {
            if (dna.charAt(index) == 'C' || dna.charAt(index) == 'G') {
                cAndGCount = cAndGCount + 1;
            }
            index = index + 1;
        }

        cgRatio = cAndGCount / ((double) dna.length());
        return cgRatio;
    }

    public StorageResource getAllGenes(String dna)   {
        StorageResource geneList = new StorageResource();
        int startIndex = dna.indexOf("ATG");
        while ( true )  {
            String currentGene = findGene(dna,startIndex);

            if (currentGene.isEmpty())  {
                break;
            }
            geneList.add(currentGene);
            startIndex = dna.indexOf("ATG",(dna.indexOf(currentGene,startIndex) +
                    currentGene.length()));
        }

        return geneList;
    }

    public String findGene(String dna, int startIndex)  {
        if (startIndex == -1)   {
            return "";
        }
        int TAAIndex = findStopCodon(dna,startIndex,"TAA");
        int TAGIndex = findStopCodon(dna,startIndex,"TAG");
        int TGAIndex = findStopCodon(dna,startIndex,"TGA");
        int minIndex = 0;
        if (TAAIndex == -1 || TAGIndex != -1 && TAGIndex < TAAIndex)    {
            minIndex = TAGIndex;
        }
        else    {
            minIndex = TAAIndex;
        }
        if (minIndex == -1 || TGAIndex != -1 && TGAIndex < minIndex)    {
            minIndex = TGAIndex;
        }
        if (minIndex == -1){
            return "";
        }
        return dna.substring(startIndex,minIndex+3);
    }

    public int findStopCodon(String dna, int startIndex, String stopCodon)  {
        int currIndex = dna.indexOf(stopCodon, startIndex);
        while (currIndex != -1) {
            if ((currIndex - startIndex) % 3 == 0)  {
                return currIndex;
            }
            else    {
                currIndex = dna.indexOf("TAA",currIndex+1);
            }
        }
        return currIndex;
    }

    public void printAllGenes(String dna)  {
        int totalNumberOfGenes = 0;
        int startIndex = dna.indexOf("ATG");
        while ( true )  {
            String currentGene = findGene(dna,startIndex);

            if (currentGene.isEmpty())  {
                break;
            }
            System.out.println(currentGene);
            totalNumberOfGenes = totalNumberOfGenes + 1;
            startIndex = dna.indexOf("ATG",(dna.indexOf(currentGene,startIndex) +
                    currentGene.length()));
        }
        System.out.println("Total number of genes: " + totalNumberOfGenes);
    }

    public static void main(String[] args){
        Part3 p3 = new Part3();
        p3.testProcessGenes2();
    }
}
    