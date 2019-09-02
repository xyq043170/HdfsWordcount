import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class HdfsWordcount {
    public static void main(String[] args) throws Exception {
        Properties props =new Properties();
        props.load(HdfsWordcount.class.getClassLoader().getResourceAsStream("job.properties"));

        Path inpath = new Path(props.get("INPUT_PATH").toString());
        Path outpath = new Path(props.get("OUTPUT_PATH").toString());


        Class<?> mapper_class=Class.forName(props.getProperty("MAPPER_CLASS"));
        Mapper mapper = (Mapper)mapper_class.newInstance();

        Context context = new Context();

        //hdfs读文件，一次读一行
        FileSystem fs=FileSystem.get(new URI("hdfs://192.168.11.197:9000"),new Configuration(),"root");

        RemoteIterator<LocatedFileStatus> lists = fs.listFiles(inpath,false);

        //对每一行进行业务处理
        while (lists.hasNext())
        {
            LocatedFileStatus file =lists.next();
            FSDataInputStream in =fs.open(file.getPath());
            BufferedReader br =new BufferedReader(new InputStreamReader(in));
            String line =null;
            while ((line= br.readLine()) != null)
            {
                //将这一行的处理结果放入缓存
                mapper.map(line,context);
            }
            br.close();
            in.close();
        }

        if(fs.exists(outpath))
        {
            throw new RuntimeException("指定的输出目录已存在，请更新！");
        }
        else
        {
            fs.mkdirs(outpath);
        }


        //将缓存中的结果数据输出到HDFS结果文件
        HashMap<Object,Object> map = context.getContextMap();
        FSDataOutputStream out =fs.create(new Path(outpath,new Path("res.dat")));

        Set<Map.Entry<Object,Object>> entrySet = map.entrySet();
        for (Map.Entry<Object,Object> entry:entrySet)
        {
           out.write((entry.getKey().toString()+"\t"+entry.getValue()+"\n").getBytes());
        }

        out.close();
        fs.close();

        System.out.println("恭喜数据统计完成!");
    }
}
