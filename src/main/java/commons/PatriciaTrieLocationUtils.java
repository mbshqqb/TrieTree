package commons;

import au.com.bytecode.opencsv.CSVReader;
import bean.Location;

import org.apache.commons.collections4.trie.PatriciaTrie;

import java.io.IOException;
import java.io.InputStreamReader;
//北京市：正确匹配到北京
//北京：正确匹配到北京市
//北京市朝阳区：正确匹配到北京，若有北京市，则优先匹配到北京市
//北京朝阳区：正确匹配到北京市，若有北京朝阳区，则优先匹配到北京朝阳区
//北平：报错
//
//
//dat.commonPrefixSearch        北京xx->北京
//patriciaTrie.prefixMap       北京->北京xx
//patriciaTrie.select           北京xx->北京    北京xx->北京yy

public class PatriciaTrieLocationUtils {
    private PatriciaTrie<Location> patriciaTrie;
    public static void main(String[] args) throws IOException {
        PatriciaTrieLocationUtils patriciaTrieLocationUtils=new PatriciaTrieLocationUtils("/sys_area.csv");
        System.out.println(patriciaTrieLocationUtils.patriciaTrie.prefixMap("北京"));
        System.out.println(patriciaTrieLocationUtils.patriciaTrie.select("北京"));
    }
    public PatriciaTrieLocationUtils(String fileName) throws IOException {
        CSVReader csvReader=new CSVReader(new InputStreamReader(PatriciaTrieLocationUtils.class.getResourceAsStream(fileName)));
        String[] line;
        patriciaTrie=new PatriciaTrie<Location>();
        while((line=csvReader.readNext())!=null){
            Location location=new Location(line[0],line[1],line[2],line[3],line[4],line[5],line[6]);
            patriciaTrie.put(location.getName(),location);
        }
    }
}