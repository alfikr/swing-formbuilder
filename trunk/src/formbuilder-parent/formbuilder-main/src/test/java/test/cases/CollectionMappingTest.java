package test.cases;

import domain.Account;
import domain.Person;
import org.fest.swing.fixture.JListFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.main.Builder;
import org.formbuilder.main.Form;
import org.formbuilder.main.map.type.CollectionMapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.env.FormEnvironment;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.elementsEqual;
import static java.util.Arrays.asList;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 16:20:09
 */
public class CollectionMappingTest
{
    private FormEnvironment env;

    @BeforeClass
    public void setUp()
            throws
            Exception
    {
        this.env = new FormEnvironment();
        this.env.setUp( this );
    }

    @AfterClass
    public void tearDown()
            throws
            Exception
    {
        this.env.tearDown( this );
    }

    @Test
    public void testCollectionEditor()
            throws
            InterruptedException
    {
        final Form<Person> form = env.buildFormInEDT( Builder.map( Person.class ).use( new AccountSetMapper() ) );
        env.addToWindow( form );

        final Person oldValue = env.createPerson();
        env.setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        JListFixture accsList = wrapperPanel.list( "goodAccounts" );
        accsList.requireItemCount( 2 );

        accsList.selectItem( "acc1" ).requireSelection( "acc1" );
        assert elementsEqual( form.getValue().getGoodAccounts(), asList( new Account( "acc1" ) ) );
    }

    private static class AccountSetMapper
            extends CollectionMapper<Account, Set>
    {
        @Override
        protected Collection<Account> getSuitableData()
        {
            return asList( new Account( "acc1" ), new Account( "acc2" ) );
        }

        @Override
        protected Set<Account> refine( final List<Account> selectedValues )
        {
            return new HashSet<Account>( selectedValues );
        }

        @Override
        public Class<Set> getValueClass()
        {
            return Set.class;
        }
    }
}
