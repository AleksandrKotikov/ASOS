package com.fragilebytes.asos.models.category;

/**
 * Created by Windows on 10/04/2016.
 */
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Facet {

    @SerializedName("FacetValues")
    @Expose
    private List<FacetValue> FacetValues = new ArrayList<FacetValue>();
    @SerializedName("Id")
    @Expose
    private String Id;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Sequence")
    @Expose
    private Integer Sequence;

    /**
     *
     * @return
     * The FacetValues
     */
    public List<FacetValue> getFacetValues() {
        return FacetValues;
    }

    /**
     *
     * @param FacetValues
     * The FacetValues
     */
    public void setFacetValues(List<FacetValue> FacetValues) {
        this.FacetValues = FacetValues;
    }

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The Id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The Name
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @param Name
     * The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     *
     * @return
     * The Sequence
     */
    public Integer getSequence() {
        return Sequence;
    }

    /**
     *
     * @param Sequence
     * The Sequence
     */
    public void setSequence(Integer Sequence) {
        this.Sequence = Sequence;
    }

}