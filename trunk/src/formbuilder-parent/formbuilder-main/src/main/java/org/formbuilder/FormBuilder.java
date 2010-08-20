/*
 * Copyright (C) 2010 Andrey Yeremenok (eav1986__at__gmail__com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

/**
 *
 */
package org.formbuilder;

import org.formbuilder.mapping.BeanMapping;
import org.formbuilder.mapping.BeanMappingContext;
import org.formbuilder.mapping.form.BeanRelpicatingForm;
import org.formbuilder.mapping.MappingRules;
import org.formbuilder.mapping.beanmapper.GridBagMapper;
import org.formbuilder.mapping.beanmapper.PropertyNameBeanMapper;
import org.formbuilder.mapping.beanmapper.SampleBeanMapper;
import org.formbuilder.mapping.exception.MappingException;
import org.formbuilder.mapping.typemapper.BooleanToCheckboxMapper;
import org.formbuilder.mapping.typemapper.DateToSpinnerMapper;
import org.formbuilder.mapping.typemapper.NumberToSpinnerMapper;
import org.formbuilder.mapping.typemapper.StringToTextFieldMapper;
import org.formbuilder.validation.ValidateOnChange;
import org.formbuilder.validation.ValidationMarker;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.validation.Validator;
import java.awt.*;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The entry point to form building. Collects configuration and builds {@link Form}s using it. <br> <h1>Usage:</h1>
 * <p/>
 * <pre>
 * Form&lt;Person&gt; form = FormBuilder.map( Person.class ).with( new SampleBeanMapper&lt;Person&gt;()
 *                   {
 *                       &#064;Override
 *                       protected JComponent mapBean( Person beanSample )
 *                       {
 *                           Box box = Box.createHorizontalBox();
 *                           box.add( label( beanSample.getName() ) );
 *                           box.add( editor( beanSample.getName() ) );
 *                           return box;
 *                       }
 *                   } ).buildForm();
 * JComponent formPanel = form.asComponent();
 * </pre>
 *
 * @author aeremenok 2010
 */
@NotThreadSafe
public class FormBuilder<B>
{
    private final Class<B> beanClass;
    private final MappingRules mappingRules = new MappingRules();
    @Nonnull
    private BeanMapper<B> beanMapper = new GridBagMapper<B>();
    private boolean doValidation = true;

    private FormBuilder( final Class<B> beanClass )
    {
        this.beanClass = beanClass;
    }

    /**
     * Starts building of the form for the given class.
     *
     * @param <T>       beanmapper typemapper
     * @param beanClass beanmapper class object
     * @return builder instance, configured for the given class
     */
    @Nonnull
    public static <T> FormBuilder<T> map( @Nonnull final Class<T> beanClass )
    {
        return new FormBuilder<T>( checkNotNull( beanClass ) );
    }

    /**
     * Does the building of editor components and their layout on the form component. <u>This method should be called in
     * Event Dispatch Thread</u>
     *
     * @return an instance of the {@link Form}, ready to be added to swing {@link Container}s
     */
    @Nonnull
    public Form<B> buildForm()
    { // todo pass a copy of mapping rules
        final BeanMappingContext<B> context = new BeanMappingContext<B>( beanClass, mappingRules, doValidation );
        final BeanMapping mapping = beanMapper.map( context );
        return new BeanRelpicatingForm<B>( mapping, beanClass );
    }

    /**
     * Turns on/off the validation. <u>By default, the validation is turned <b>on</b></u>
     *
     * @param doValidation true if the form should perform validation after changing the editor components
     * @return this builder
     *
     * @see Validator
     * @see ValidationMarker
     * @see ValidateOnChange
     */
    @Nonnull
    public FormBuilder<B> doValidation( final boolean doValidation )
    {
        this.doValidation = doValidation;
        return this;
    }

    /**
     * Binds types of beanmapper properties to custom editor components.<br> By default, {@link StringToTextFieldMapper},
     * {@link NumberToSpinnerMapper}, {@link DateToSpinnerMapper} and {@link BooleanToCheckboxMapper} are already
     * registered.<br> <br> Primitive and wrapper types are considered the same way. For example, if you pass a mapper
     * of the {@link Integer} class, it will also be used for mapping of <code>int</code> properties. <br> <br> During
     * the mapping, if a mapper for some property typemapper cannot be found, an attempt to find a mapper for its supertype is
     * performed. If it is failed, {@link MappingException} is raised, and by default, the propery is skipped. That
     * means, for example, that {@link NumberToSpinnerMapper} suits for int, long, {@link BigDecimal}, etc...
     *
     * @param typeMappers mappers for each custom typemapper
     * @return this builder
     *
     * @see MappingRules
     */
    @Nonnull
    public FormBuilder<B> use( @Nonnull final TypeMapper... typeMappers )
    {
        for ( final TypeMapper typeMapper : typeMappers )
        {
            this.mappingRules.addMapper( checkNotNull( typeMapper ) );
        }
        return this;
    }

    /**
     * Allows to perform a <u>checked</u> mapping for properties, specified by user. For example, the following code
     * orders to use a <code>StringToTextAreaMapper</code> for property description, while other {@link String}
     * properties are mapped by default:
     * <p/>
     * <pre>
     * Form&lt;Person&gt; form = FormBuilder.map( Person.class ).useForGetters( new GetterMapper&lt;Person&gt;()
     *                   {
     *                       &#064;Override
     *                       protected void mapGetters( final Person beanSample )
     *                       {
     *                           mapGetter( beanSample.getDescription(), new StringToTextAreaMapper() );
     *                       }
     *                   } ).buildForm();
     * </pre>
     *
     * @param getterMapper an implementation of {@link GetterMapper}, where the user can specify property bindings
     * @return this builder
     *
     * @see MappingRules
     */
    @Nonnull
    public FormBuilder<B> useForGetters( @Nonnull final GetterMapper<B> getterMapper )
    {
        getterMapper.mapGettersToRules( beanClass, mappingRules );
        return this;
    }

    /**
     * Allows to perform an <u>unchecked</u> mapping for a property, specified by user. For example, the following code
     * orders to use a <code>StringToTextAreaMapper</code> for property description, while other {@link String}
     * properties are mapped by default:
     * <p/>
     * <pre>
     * Form&lt;Person&gt; form = FormBuilder.map( Person.class ).useForProperty( &quot;description&quot;, new
     * StringToTextAreaMapper() )
     *                           .buildForm();
     * </pre>
     *
     * @param propertyName   the name of beanmapper property
     * @param propertyMapper the mapper to use for it
     * @return this builder
     *
     * @see MappingRules
     */
    @Nonnull
    public FormBuilder<B> useForProperty( @Nonnull final String propertyName,
                                          @Nonnull final TypeMapper propertyMapper )
    {
        this.mappingRules.addMapper( checkNotNull( propertyName ), checkNotNull( propertyMapper ) );
        return this;
    }

    /**
     * Allows to control the building of the entire form component using a custom {@link BeanMapper}.
     *
     * @param beanMapper the custom mapper to use
     * @return this builder
     *
     * @see BeanMapper
     * @see GridBagMapper
     * @see SampleBeanMapper
     * @see PropertyNameBeanMapper
     */
    @Nonnull
    public FormBuilder<B> with( @Nonnull final BeanMapper<B> beanMapper )
    {
        this.beanMapper = checkNotNull( beanMapper );
        return this;
    }
}
