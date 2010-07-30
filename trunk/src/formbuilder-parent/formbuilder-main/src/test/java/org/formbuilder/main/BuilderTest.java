/**
 *
 */
package org.formbuilder.main;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.fixture.JListFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.formbuilder.main.map.bean.SampleBeanMapper;
import org.formbuilder.main.map.type.CollectionMapper;
import org.formbuilder.main.map.type.ReferenceMapper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.env.ComponentEnvironment;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import static com.google.common.collect.Iterables.elementsEqual;
import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotSame;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

/**
 * @author aeremenok 2010
 */
public class BuilderTest
{
    private ComponentEnvironment<JPanel> env;

    @Test( dependsOnMethods = "setAndGetValue" )
    public void customizedLayout()
    {
        assert !SwingUtilities.isEventDispatchThread();
        final Form<Person> form = buildFormInEDT( Builder.from( Person.class ).mapBeanWith( new SampleBeanMapper<Person>()
        {
            @Override
            public JComponent map( final Person beanTemplate )
            {
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( component( beanTemplate.getName() ) );
                return panel;
            }
        } ) );

        final JComponent component = form.asComponent();
        verifyLayout( component, JPanel.class, BorderLayout.class );
        addToWindow( component );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "name" );

        try
        {
            assert wrapperPanel.spinner( "age" ) == null;
            fail();
        }
        catch ( final Exception ignored )
        {}
    }

    @Test
    public void setAndGetValue()
    {
        final Form<Person> form = buildFormInEDT( Builder.from( Person.class ) );

        final JComponent component = form.asComponent();
        verifyLayout( component, JPanel.class, GridBagLayout.class );
        addToWindow( component );

        final Person oldValue = createPerson();
        setValueInEDT( form, oldValue );
        requireNewBeanCreated( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        wrapperPanel.label( "name" ).requireText( "Person's first name" );
        wrapperPanel.label( "age" ).requireText( "Age" );
        wrapperPanel.label( "birthDate" ).requireText( "Date of birth" );

        wrapperPanel.textBox( "name" ).requireText( oldValue.getName() );
        wrapperPanel.spinner( "age" ).requireValue( oldValue.getAge() );
        wrapperPanel.spinner( "birthDate" ).requireValue( oldValue.getBirthDate() );
    }

    @Test( dependsOnMethods = "customizedLayout" )
    public void testValidation()
            throws
            InterruptedException
    {
        final Form<Person> form = buildFormInEDT( Builder.from( Person.class ) );
        addToWindow( form.asComponent() );

        final Person oldValue = createPerson();
        setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        JTextComponentFixture nameTextBox = wrapperPanel.textBox( "name" );

        assertNotSame( nameTextBox.target.getBackground(), Color.PINK );
        assertNull( nameTextBox.target.getToolTipText() );
        nameTextBox.setText( "ee" );
        assertEquals( nameTextBox.target.getBackground(), Color.PINK );
        nameTextBox.requireToolTip( "size must be between 3 and 2147483647" );
    }

    @Test( dependsOnMethods = "testValidation" )
    public void testReferenceEditor()
            throws
            InterruptedException
    {
        final Form<Person> form = buildFormInEDT( Builder.from( Person.class ).use( new RoleMapper() ) );
        addToWindow( form.asComponent() );

        final Person oldValue = createPerson();
        setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        final JComboBoxFixture roleCombo = wrapperPanel.comboBox( "role" );
        roleCombo.requireItemCount( 2 );

        roleCombo.selectItem( 0 ).requireSelection( "admin" );
        assertEquals( form.getValue().getRole(), new Role( "admin" ) );

        roleCombo.selectItem( 1 ).requireSelection( "user" );
        assertEquals( form.getValue().getRole(), new Role( "user" ) );
    }

    @Test( dependsOnMethods = "testReferenceEditor" )
    public void testCollectionEditor()
            throws
            InterruptedException
    {
        final Form<Person> form = buildFormInEDT( Builder.from( Person.class ).use( new AccountSetMapper() ) );
        addToWindow( form.asComponent() );

        final Person oldValue = createPerson();
        setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        JListFixture accsList = wrapperPanel.list( "goodAccounts" );
        accsList.requireItemCount( 2 );

        accsList.selectItem( "acc1" ).requireSelection( "acc1" );
        assert elementsEqual( form.getValue().getGoodAccounts(), asList( new Account( "acc1" ) ) );
    }

    private void requireNewBeanCreated( final Form<Person> form,
                                        final Person oldValue )
    {
        final Person newValue = form.getValue();
        assertEquals( newValue, oldValue );
        assert form.getValue() != oldValue;
    }

    private Person createPerson()
    {
        final Person oldValue = new Person();
        oldValue.setName( "oldValue" );
        oldValue.setAge( 24 );
        oldValue.setBirthDate( new Date( 1 ) );
        return oldValue;
    }

    private <B> Form<B> buildFormInEDT( final Builder<B> builder )
    {
        return GuiActionRunner.execute( new GuiQuery<Form<B>>()
        {
            @Override
            protected Form<B> executeInEDT()
            {
                return builder.buildForm();
            }
        } );
    }

    private <B> void setValueInEDT( final Form<B> form,
                                    final B value )
    {
        GuiActionRunner.execute( new GuiTask()
        {
            @Override
            protected void executeInEDT()
                    throws
                    Throwable
            {
                form.setValue( value );
            }
        } );
    }

    @BeforeClass
    public void setUp()
            throws
            Exception
    {
        this.env = ComponentEnvironment.fromQuery( new Callable<JPanel>()
        {
            @Override
            public JPanel call()
            {
                return new JPanel( new BorderLayout() );
            }
        } );
        this.env.setUp( this );
    }

    @AfterClass
    public void tearDown()
            throws
            Exception
    {
        this.env.tearDown( this );
    }

    private void addToWindow( final JComponent component )
    {
        final JPanel panel = env.getComponent();
        panel.removeAll();
        panel.add( component );
        env.getFrameFixture().component().pack();
    }

    private void verifyLayout( final JComponent component,
                               final Class<? extends JComponent> superClass,
                               final Class<? extends LayoutManager> layoutClass )
    {
        assert superClass.isAssignableFrom( component.getClass() ) : component;
        assert layoutClass.isAssignableFrom( component.getLayout().getClass() ) : component.getLayout();
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
        public Class<Set> valueClass()
        {
            return Set.class;
        }
    }
}
