package domain;

/**
 * @author aeremenok
 *         Date: 09.08.2010
 *         Time: 17:29:02
 */
public class Address
{
    private String country;
    private String city;
    private int house;

    public String getCity()
    {
        return city;
    }

    public String getCountry()
    {
        return country;
    }

    public int getHouse()
    {
        return house;
    }

    public void setCity( final String city )
    {
        this.city = city;
    }

    public void setCountry( final String country )
    {
        this.country = country;
    }

    public void setHouse( final int house )
    {
        this.house = house;
    }
}