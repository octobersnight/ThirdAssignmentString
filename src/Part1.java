
import edu.duke.FileResource;
import edu.duke.StorageResource;

public class Part1 {
    /**
     * this method is to find the first occurrence of the stop codon.
     * the dna must be a multiple of 3, if not return empty string.
     */
    public int findStopCodon(String dna, String stopCodon, int startIndex){
        int currentOccurrence = dna.indexOf(stopCodon, startIndex + 3);     /* position of the stopCodon in the dna. */

        while (currentOccurrence != -1) {
            if ((currentOccurrence - startIndex) % 3 == 0) {
                return currentOccurrence;                   /* return the length from the start to the occurrence stop codon */
            } else {
                currentOccurrence = dna.indexOf(stopCodon, currentOccurrence + 1);          /* return the length of the actual dna strings */
            }
        }
        return -1;
    }

    /**
     *
     */
    public String findGene(String dna, int whereToStart){
        int minIndex = 0;
        String result;

       // whereToStart = dna.indexOf("ATG", whereToStart);

        if (whereToStart == -1 ){
            return "";
        }

        // there are more than one type of stop codon
        int taaIndex = findStopCodon(dna, "TAA", whereToStart);
        int tgaIndex = findStopCodon(dna, "TGA", whereToStart);
        int tagIndex = findStopCodon(dna, "TAG", whereToStart);

        //check to see which stop codon is the shortest between taa and tga
        if(taaIndex == -1 || tgaIndex != -1 && tgaIndex < taaIndex){
            minIndex = tgaIndex;
        } else {
            minIndex = taaIndex;
        }

        // check to see if which stop codon is the shortest between tag and minIndex
        if (minIndex == -1 || tagIndex != -1 && tagIndex < minIndex){
            minIndex = tagIndex;
        }

        if (minIndex == -1){
            return "";
        }

        return dna.substring(whereToStart, minIndex + 3);

    }

    /**
     *  creating StorageResource function to stored all the gene
     */
    public StorageResource getAllGene(String dna){
        StorageResource geneList = new StorageResource();
        int currentIndex = 0;

        while(true){
            String currentGene = findGene(dna, currentIndex);

            if (currentGene.isEmpty()){
                break;
            }

            geneList.add(currentGene);

            currentIndex = dna.indexOf(currentGene, currentIndex + 3) + currentGene.length();

        }
        return geneList;
    }

    /**
     *  The method named countGenes that has a String parameter named dna representing a string of DNA.
     *  This method returns the number of genes found in dna.
     */
    int countGenes(String dna){
        int totalCount = 0;
        int currentIndex = 0;

        while(true){
            String currentGene = findGene(dna, currentIndex);
            if (currentGene.isEmpty()){
                break;
            }
            totalCount = totalCount + 1;
            currentIndex = dna.indexOf(currentGene, currentIndex + 3) + currentGene.length();
        }
        return totalCount;
    }
    /**
     *  This function is to return a value ratio of c's and g's
     */
    public double cgRatio(String dna){
        int currentIndexC = 0;
        int currentIndexG = 0;
        int totalG = 0;
        int totalC = 0;
        float result;

        //create an index for c's and g's
        int gIndex = dna.indexOf("G", currentIndexG);
        int cIndex = dna.indexOf("C", currentIndexC);

        // creating loop to read the initial value then break
        // and continue until not more value are left
        while ( true ) {
            if ( currentIndexC == -1){
                break;
            }
            // update the currentIndex
            currentIndexC = dna.indexOf("C", currentIndexC + 1);

            // total of g's and c's
            totalC = totalC + 1;
        }

        while ( true ) {
            // update the currentIndex
            currentIndexG = dna.indexOf("G", currentIndexG + 1);

            if ( currentIndexG == -1){
                break;
            }
            // total of g's and c's
            totalG = totalG + 1;
        }

        result = (float)(totalC + totalG) / dna.length();
        return result;
        /*
        System.out.println("The total of C's together: " + totalC);
        System.out.println("The total of G's together: " + totalG);
        System.out.println("The Ratio of G's and C's together: " + result );
        System.out.println("from the length of DNA: " + dna.length());
         */

    }



    public void processGene(StorageResource sr){
        String longerThan9;
        int moreThan9 = 0;
        String cgRatio;
        String numCGRatio;
        int longestString;
        int currentLongestString = 0;

        for (String gene : sr.data()){
            // print all the string in sr that are longer than 9 characters
            // print all number of strings in sr that are longer than 9 characters
            if (gene.length() > 9){
                System.out.println("Gene is greater than 9 character: " + gene);
                moreThan9 = moreThan9 + 1;
            }

            // print the strings in sr whose C-G_ratio is higher than 0.35
            if (cgRatio(gene) > 0.36){
                System.out.println("C-G ratio is: " + cgRatio(gene));
            } else {
                System.out.println("Not higher than .35 ratio.");
            }
            // print the number of strings in sr whose C-G_ratio is higher than 0.35

            // print the length of the longest gene is s
            longestString = gene.length();
            if ( longestString > currentLongestString){
                currentLongestString = longestString;
            }
        }
        System.out.println("Number of Gene that is greater than 9 characters: " + moreThan9);
        System.out.println("The longest Gene: " + currentLongestString);
    }

    public void test(){
        StorageResource sr = new StorageResource();
        //FileResource fr = new FileResource("/home/kazuki/IdeaProjects/ThirdAssignmentString/brca1line.fa");
        FileResource fr = new FileResource("/home/kazuki/IdeaProjects/ThirdAssignmentString/GRch38dnapart.fa");
        String dna = fr.asString();

        sr = getAllGene(dna);
        System.out.println("DNA Strands: ");
        for (String st : sr.data()){
            System.out.println("- " + st);
        }

        processGene(sr);

    }

    public static void main(String[] args){
        Part1 t2 = new Part1();
        t2.test();
    }
}
