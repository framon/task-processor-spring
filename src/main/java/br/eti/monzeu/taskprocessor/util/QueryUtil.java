package br.eti.monzeu.taskprocessor.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Ex: cityCode:1234 name:(GAR L) Rio
 *
 * cityCode = 1234
 * name = GAR L
 * null = Rio
 */
public class QueryUtil {

  private static final String queryRgx2 = "(\".+?\"|(\\w+:)?(\\w+|\\(.+?\\)))";
  private static final String queryRgx = "(?:(?<key>\\w+):(?:(?:\\((?<v2>.+?)\\))|(?:(?<v1>\\S+))))|(?<flat>\".+?\"|\\S+)";

  private static final Pattern queryPtrn = Pattern.compile(queryRgx);

  private QueryUtil() {
  }

  public static Iterable<KeyValue> split(String src) {

//        Stream.generate(s)
//        Iterator it = myCustomIteratorThatGeneratesTheSequence();
//        StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, Spliterator.DISTINCT), false);
    ArrayList<KeyValue> r = new ArrayList<>();

    Matcher matcher = queryPtrn.matcher(src);
    while (matcher.find()) {
      String flat = matcher.group("flat");
      String key = matcher.group("key");
      String value = matcher.group("v2");
      if (value == null) {
        value = matcher.group("v1");
      }

      if (flat != null) {
        r.add(new KeyValue(null, flat));
      } else {
        r.add(new KeyValue(key, value));
      }

    }

    return r;

  }

  public static final class KeyValue {

    private String key;
    private String value;

    public KeyValue(String key, String value) {
      this.key = key;
      this.value = value;
    }

    public String getKey() {
      return key;
    }

    public String getValue() {
      return value;
    }

  }
}
