package domain;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 13:53:28
 */
public class Role
{
    private String name;

    public Role()
    {
    }

    public Role( final String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public boolean equals( final Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof Role ) )
        {
            return false;
        }

        final Role role = (Role) o;

        if ( name != null ? !name.equals( role.name ) : role.name != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        return name != null ? name.hashCode() : 0;
    }
}
