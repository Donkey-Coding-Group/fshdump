package fshdump;

public interface DataFeed {

    public boolean ownsContent();

    public DataFeed[] listContent();

    public String getData();

}
