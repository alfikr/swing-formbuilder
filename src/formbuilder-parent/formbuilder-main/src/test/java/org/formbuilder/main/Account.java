/**
 *
 */
package org.formbuilder.main;

/**
 * @author aeremenok 2010
 */
public class Account
{
    private String code;

    public Account( final String code )
    {
        this.code = code;
    }

    public Account()
    {
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( final String code )
    {
        this.code = code;
    }

    private Person person;
    private int amount;

    public int getAmount()
    {
        return this.amount;
    }

    public Person getPerson()
    {
        return this.person;
    }

    public void setAmount( final int amount )
    {
        this.amount = amount;
    }

    public void setPerson( final Person person )
    {
        this.person = person;
    }

    @Override
    public String toString()
    {
        return code;
    }

    @Override
    public boolean equals( final Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof Account ) )
        {
            return false;
        }

        final Account account = (Account) o;

        if ( amount != account.amount )
        {
            return false;
        }
        if ( code != null ? !code.equals( account.code ) : account.code != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + amount;
        return result;
    }
}
