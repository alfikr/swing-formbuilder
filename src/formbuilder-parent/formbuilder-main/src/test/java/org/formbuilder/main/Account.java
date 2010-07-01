/**
 * 
 */
package org.formbuilder.main;

/**
 * @author aeremenok 2010
 */
public class Account
{
    private Person person;

    private int    amount;

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

}
