package model.domain.message;

import java.io.Serializable;
import java.util.Collection;

/**
 * A class representing a query
 * Created by Michael on 2015/09/12.
 */
public class Reply extends Message implements Serializable{
    private Collection<Integer> queryIds;

    public void setQueryIds(Collection<Integer> queryIds)
    {
        this.queryIds = queryIds;
    }

    public Collection<Integer> getQueryIds()
    {
        return queryIds;
    }
}
