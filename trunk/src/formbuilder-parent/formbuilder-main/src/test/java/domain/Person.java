/**
 *
 */
package domain;

import org.main.annotations.UIHidden;
import org.main.annotations.UIReadOnly;
import org.main.annotations.UITitle;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * @author aeremenok 2010
 */
public class Person
{
    private String name;
    private String description;
    private int age;
    private Date birthDate;
    private Set<Account> goodAccounts;
    private Set<Account> badAccounts;
    private Role role;
    private long id;

    @UIHidden
    public long getId()
    {
        return id;
    }

    public void setId( final long id )
    {
        this.id = id;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( final String description )
    {
        this.description = description;
    }

    public Set<Account> getBadAccounts()
    {
        return badAccounts;
    }

    public void setBadAccounts( final Set<Account> badAccounts )
    {
        this.badAccounts = badAccounts;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole( final Role role )
    {
        this.role = role;
    }

    public Set<Account> getGoodAccounts()
    {
        return this.goodAccounts;
    }

    public int getAge()
    {
        return this.age;
    }

    @UIReadOnly
    @UITitle( "Date of birth" )
    public Date getBirthDate()
    {
        return this.birthDate;
    }

    @Size( min = 3 )
    @UITitle( "Person's first name" )
    public String getName()
    {
        return this.name;
    }

    public void setGoodAccounts( final Set<Account> goodAccounts )
    {
        this.goodAccounts = goodAccounts;
    }

    public void setAge( final int age )
    {
        this.age = age;
    }

    public void setBirthDate( final Date birthDate )
    {
        this.birthDate = birthDate;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    @Override
    public boolean equals( final Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof Person ) )
        {
            return false;
        }

        final Person person = (Person) o;

        if ( age != person.age )
        {
            return false;
        }
        if ( birthDate != null ? !birthDate.equals( person.birthDate ) : person.birthDate != null )
        {
            return false;
        }
        if ( name != null ? !name.equals( person.name ) : person.name != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + ( birthDate != null ? birthDate.hashCode() : 0 );
        return result;
    }

    @Override
    public String toString()
    {
        return "Person{" + "name='" + name + '\'' + ", age=" + age + ", birthDate=" + birthDate + '}';
    }
}
