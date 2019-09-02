public class WordCountMapper implements Mapper{
    @Override
    public void map(String line, Context context) {
        String[] words = line.split("");

        for (String word:words) {
            Object value = context.get(word);
            if (null == value)
            {
                context.write(word,1);
            }
            else
            {
                int v = (int)value+1;
                context.write(word,v);
            }
        }
    }
}
