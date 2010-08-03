package test.cases;

import domain.Account;
import domain.Person;
import org.fest.swing.fixture.JListFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.mapping.type.CollectionToJListMapper;
import org.testng.annotations.Test;

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
        extends FormTest
{
    @Test
    public void testCollectionEditor()
    {
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ).use( new AccountSetMapper() ) );
        env.addToWindow( form );

        final Person oldValue = env.createPerson();
        env.setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        final JListFixture accsList = wrapperPanel.list( "goodAccounts" );
        accsList.requireItemCount( 2 );

        accsList.selectItem( "acc1" ).requireSelection( "acc1" );
        assert elementsEqual( form.getValue().getGoodAccounts(), asList( new Account( "acc1" ) ) );
    }

    private static class AccountSetMapper
            extends CollectionToJListMapper<Account, Set>
    {
        @Override
        public Class<Set> getValueClass()
        {
            return Set.class;
        }

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
    }
}
