package cn.com.eship.model;

/**
 * Created by simon on 16/9/13.
 */
public class Words {
    private String id;
    private String word;
    private KindDic kindDic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public KindDic getKindDic() {
        return kindDic;
    }

    public void setKindDic(KindDic kindDic) {
        this.kindDic = kindDic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Words words = (Words) o;

        if (id != null ? !id.equals(words.id) : words.id != null) return false;
        if (word != null ? !word.equals(words.word) : words.word != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        return result;
    }
}
