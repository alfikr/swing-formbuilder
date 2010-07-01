/**
 * 
 */
package org.formbuilder.main;

import java.util.Date;
import java.util.Set;

import org.formbuilder.main.annotations.Field;

/**
 * @author aeremenok 2010
 */
public class Person
{
    private String       name;
    private int          age;
    private Date         birthDate;

    private Set<Account> accounts;

    public Set<Account> getAccounts()
    {
        return this.accounts;
    }

    public int getAge()
    {
        return this.age;
    }

    @Field( title = "Date of birth" )
    public Date getBirthDate()
    {
        return this.birthDate;
    }

    @Field( title = "Person's first name" )
    public String getName()
    {
        return this.name;
    }

    public void setAccounts( final Set<Account> accounts )
    {
        this.accounts = accounts;
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
}
