package darts;

import au.com.bytecode.opencsv.CSVReader;
import bean.Location;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrieLocationUtils {
    private DoubleArrayTrie dat;
    private List<String> wordList;
    public static void main(String[] args) throws IOException {
        TrieLocationUtils trieLocationUtils=new TrieLocationUtils("/test.csv");
        System.out.println(trieLocationUtils.wordList.get(trieLocationUtils.dat.commonPrefixSearch("").get(0)));
//        trieLocationUtils.getLocation();
    }
    public Location getLocation(String path){
        return null;
    }
    public TrieLocationUtils(String fileName) throws IOException {
        CSVReader csvReader=new CSVReader(new InputStreamReader(TrieLocationUtils.class.getResourceAsStream(fileName)));
        String[] line;
        wordList = new ArrayList<String>();
        while((line=csvReader.readNext())!=null){
            wordList.add(line[0]);
        }
        Collections.sort(wordList);
        dat = new DoubleArrayTrie();
        dat.build(wordList);
    }
}