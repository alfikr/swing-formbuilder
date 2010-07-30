package org.formbuilder.main;

import domain.Person;
import domain.Role;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.main.map.type.ReferenceMapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.env.FormEnvironment;

import java.util.List;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;

/**
 * @author aeremenok
 *         Date: 30.07.2010
 *         Time: 16:17:38
 */
public class ReferenceMappingTest
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
    public void testReferenceEditor()
            throws
            InterruptedException
    {
        final Form<Person> form = env.buildFormInEDT( Builder.from( Person.class ).use( new RoleMapper() ) );
        env.addToWindow( form );

        final Person oldValue = env.createPerson();
        env.setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        final JComboBoxFixture roleCombo = wrapperPanel.comboBox( "role" );
        roleCombo.requireItemCount( 2 );

        roleCombo.selectItem( 0 ).requireSelection( "admin" );
        assertEquals( form.getValue().getRole(), new Role( "admin" ) );

        roleCombo.selectItem( 1 ).requireSelection( "user" );
        assertEquals( form.getValue().getRole(), new Role( "user" ) );
    }

    private static class RoleMapper
            extends ReferenceMapper<Role>
    {
        @Override
        public Class<Role> valueClass()
        {
            return Role.class;
        }

        @Override
        protected List<Role> getSuitableData()
        {
            return asList( new Role( "admin" ), new Role( "user" ) );
        }
    }
}
