package commons;

import au.com.bytecode.opencsv.CSVReader;
import bean.Location;

import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
//北京市：正确匹配到北京
//北京：正确匹配到北京市
//北京市朝阳区：正确匹配到北京，若有北京市，则优先匹配到北京市
//北京朝阳区：正确匹配到北京市朝阳区，若有北京朝阳区，则优先匹配到北京朝阳区
//北平：报错
//
//
//dat.commonPrefixSearch        北京xx->北京
//patriciaTrie.prefixMap        北京->北京xx
//patriciaTrie.select           北京xx->北京    北京xx->北京yy

public class PatriciaTrieLocationUtils {
    Map<String,PatriciaTrie<Location>>  parentChildrenMap;//id->(childname->id)
    public static void main(String[] args) throws IOException {
        PatriciaTrieLocationUtils patriciaTrieLocationUtils=new PatriciaTrieLocationUtils("/sys_area.csv");
        System.out.println(patriciaTrieLocationUtils.getLocation("北平"));
    }
    public PatriciaTrieLocationUtils(String fileName) throws IOException {
        CSVReader csvReader=new CSVReader(new InputStreamReader(PatriciaTrieLocationUtils.class.getResourceAsStream(fileName)));
        String[] line;
        Map<String,Location>idLocationMap=new HashMap<String, Location>();//id->location
        parentChildrenMap= new HashMap<String, PatriciaTrie<Location>>();//id->(childname->id)
        while((line=csvReader.readNext())!=null){
            Location location=new Location(line[0],line[1],line[2],line[3],line[4],line[5],line[6]);
            idLocationMap.put(location.getId(),location);
        }
        for(Map.Entry<String, Location> id_location: idLocationMap.entrySet()){
            Location location=id_location.getValue();
            if(parentChildrenMap.get(location.getPid())==null){
                parentChildrenMap.put(location.getPid(),new PatriciaTrie<Location>());
            }
            parentChildrenMap.get(location.getPid()).put(location.getName(),location);
        }
    }
    public Location getLocation(String path){
        Location result=null;
        String pid="0";
        int index=0;
        String temp=path;
        Location location;
        PatriciaTrie<Location> patriciaTrie;
        while((patriciaTrie=parentChildrenMap.get(pid))!=null){
            System.out.println(temp);
            location=patriciaTrie.select(temp).getValue();
            System.out.println(location.getName());
            index=StringUtils.indexOfDifference(temp,location.getName());
            if(index>0){
                temp=temp.substring(index);
                pid=location.getId();
            }else if(index==-1){
                result=location;
                break;
            }else{
                break;
            }
            if(temp.length()==0||location.getId().length()==9){
                result=location;
                break;
            }
        }
        return result;
    }
}