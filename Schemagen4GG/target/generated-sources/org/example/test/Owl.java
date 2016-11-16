/* CVS $Id: $ */
package org.example.test; 
import org.apache.jena.rdf.model.*;
import org.apache.jena.ontology.*;
 
/**
 * Vocabulary definitions from file:C:\Users\SSA_HErnst\git\Project-TripleStoreDataBase\Schemagen4GG\src/main/vocabs/owl.ttl 
 * @author Auto-generated by schemagen on 16 Nov 2016 15:45 
 */
public class Owl {
    /** <p>The ontology model that holds the vocabulary terms</p> */
    private static OntModel m_model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://www.w3.org/2002/07/owl#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    /** <p>The ontology's owl:versionInfo as a string</p> */
    public static final String VERSION_INFO = "$Date: 2009/11/15 10:54:12 $";
    
    /** <p>The object property that does not relate any two individuals.</p> */
    public static final ObjectProperty bottomObjectProperty = m_model.createObjectProperty( "http://www.w3.org/2002/07/owl#bottomObjectProperty" );
    
    /** <p>The object property that relates every two individuals.</p> */
    public static final ObjectProperty topObjectProperty = m_model.createObjectProperty( "http://www.w3.org/2002/07/owl#topObjectProperty" );
    
    /** <p>The data property that does not relate any individual to any data value.</p> */
    public static final DatatypeProperty bottomDataProperty = m_model.createDatatypeProperty( "http://www.w3.org/2002/07/owl#bottomDataProperty" );
    
    /** <p>The data property that relates every individual to every data value.</p> */
    public static final DatatypeProperty topDataProperty = m_model.createDatatypeProperty( "http://www.w3.org/2002/07/owl#topDataProperty" );
    
    /** <p>The annotation property that indicates that a given ontology is backward compatible 
     *  with another ontology.</p>
     */
    public static final AnnotationProperty backwardCompatibleWith = m_model.createAnnotationProperty( "http://www.w3.org/2002/07/owl#backwardCompatibleWith" );
    
    /** <p>The annotation property that indicates that a given entity has been deprecated.</p> */
    public static final AnnotationProperty deprecated = m_model.createAnnotationProperty( "http://www.w3.org/2002/07/owl#deprecated" );
    
    /** <p>The annotation property that indicates that a given ontology is incompatible 
     *  with another ontology.</p>
     */
    public static final AnnotationProperty incompatibleWith = m_model.createAnnotationProperty( "http://www.w3.org/2002/07/owl#incompatibleWith" );
    
    /** <p>The annotation property that indicates the predecessor ontology of a given 
     *  ontology.</p>
     */
    public static final AnnotationProperty priorVersion = m_model.createAnnotationProperty( "http://www.w3.org/2002/07/owl#priorVersion" );
    
    /** <p>The annotation property that provides version information for an ontology 
     *  or another OWL construct.</p>
     */
    public static final AnnotationProperty versionInfo = m_model.createAnnotationProperty( "http://www.w3.org/2002/07/owl#versionInfo" );
    
    /** <p>The property that determines the class that a universal property restriction 
     *  refers to.</p>
     */
    public static final OntProperty allValuesFrom = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#allValuesFrom" );
    
    /** <p>The property that determines the predicate of an annotated axiom or annotated 
     *  annotation.</p>
     */
    public static final OntProperty annotatedProperty = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#annotatedProperty" );
    
    /** <p>The property that determines the subject of an annotated axiom or annotated 
     *  annotation.</p>
     */
    public static final OntProperty annotatedSource = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#annotatedSource" );
    
    /** <p>The property that determines the object of an annotated axiom or annotated 
     *  annotation.</p>
     */
    public static final OntProperty annotatedTarget = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#annotatedTarget" );
    
    /** <p>The property that determines the predicate of a negative property assertion.</p> */
    public static final OntProperty assertionProperty = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#assertionProperty" );
    
    /** <p>The property that determines the cardinality of an exact cardinality restriction.</p> */
    public static final OntProperty cardinality = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#cardinality" );
    
    /** <p>The property that determines that a given class is the complement of another 
     *  class.</p>
     */
    public static final OntProperty complementOf = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#complementOf" );
    
    /** <p>The property that determines that a given data range is the complement of 
     *  another data range with respect to the data domain.</p>
     */
    public static final OntProperty datatypeComplementOf = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#datatypeComplementOf" );
    
    /** <p>The property that determines that two given individuals are different.</p> */
    public static final OntProperty differentFrom = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#differentFrom" );
    
    /** <p>The property that determines that a given class is equivalent to the disjoint 
     *  union of a collection of other classes.</p>
     */
    public static final OntProperty disjointUnionOf = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#disjointUnionOf" );
    
    /** <p>The property that determines that two given classes are disjoint.</p> */
    public static final OntProperty disjointWith = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#disjointWith" );
    
    /** <p>The property that determines the collection of pairwise different individuals 
     *  in a owl:AllDifferent axiom.</p>
     */
    public static final OntProperty distinctMembers = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#distinctMembers" );
    
    /** <p>The property that determines that two given classes are equivalent, and that 
     *  is used to specify datatype definitions.</p>
     */
    public static final OntProperty equivalentClass = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#equivalentClass" );
    
    /** <p>The property that determines that two given properties are equivalent.</p> */
    public static final OntProperty equivalentProperty = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#equivalentProperty" );
    
    /** <p>The property that determines the collection of properties that jointly build 
     *  a key.</p>
     */
    public static final OntProperty hasKey = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#hasKey" );
    
    /** <p>The property that determines the property that a self restriction refers to.</p> */
    public static final OntProperty hasSelf = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#hasSelf" );
    
    /** <p>The property that determines the individual that a has-value restriction refers 
     *  to.</p>
     */
    public static final OntProperty hasValue = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#hasValue" );
    
    /** <p>The property that determines the collection of classes or data ranges that 
     *  build an intersection.</p>
     */
    public static final OntProperty intersectionOf = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#intersectionOf" );
    
    /** <p>The property that determines that two given properties are inverse.</p> */
    public static final OntProperty inverseOf = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#inverseOf" );
    
    /** <p>The property that determines the cardinality of a maximum cardinality restriction.</p> */
    public static final OntProperty maxCardinality = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#maxCardinality" );
    
    /** <p>The property that determines the cardinality of a maximum qualified cardinality 
     *  restriction.</p>
     */
    public static final OntProperty maxQualifiedCardinality = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#maxQualifiedCardinality" );
    
    /** <p>The property that determines the collection of members in either a owl:AllDifferent, 
     *  owl:AllDisjointClasses or owl:AllDisjointProperties axiom.</p>
     */
    public static final OntProperty members = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#members" );
    
    /** <p>The property that determines the cardinality of a minimum cardinality restriction.</p> */
    public static final OntProperty minCardinality = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#minCardinality" );
    
    /** <p>The property that determines the cardinality of a minimum qualified cardinality 
     *  restriction.</p>
     */
    public static final OntProperty minQualifiedCardinality = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#minQualifiedCardinality" );
    
    /** <p>The property that determines the class that a qualified object cardinality 
     *  restriction refers to.</p>
     */
    public static final OntProperty onClass = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#onClass" );
    
    /** <p>The property that determines the data range that a qualified data cardinality 
     *  restriction refers to.</p>
     */
    public static final OntProperty onDataRange = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#onDataRange" );
    
    /** <p>The property that determines the datatype that a datatype restriction refers 
     *  to.</p>
     */
    public static final OntProperty onDatatype = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#onDatatype" );
    
    /** <p>The property that determines the n-tuple of properties that a property restriction 
     *  on an n-ary data range refers to.</p>
     */
    public static final OntProperty onProperties = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#onProperties" );
    
    /** <p>The property that determines the property that a property restriction refers 
     *  to.</p>
     */
    public static final OntProperty onProperty = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#onProperty" );
    
    /** <p>The property that determines the collection of individuals or data values 
     *  that build an enumeration.</p>
     */
    public static final OntProperty oneOf = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#oneOf" );
    
    /** <p>The property that determines the n-tuple of properties that build a sub property 
     *  chain of a given property.</p>
     */
    public static final OntProperty propertyChainAxiom = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#propertyChainAxiom" );
    
    /** <p>The property that determines that two given properties are disjoint.</p> */
    public static final OntProperty propertyDisjointWith = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#propertyDisjointWith" );
    
    /** <p>The property that determines the cardinality of an exact qualified cardinality 
     *  restriction.</p>
     */
    public static final OntProperty qualifiedCardinality = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#qualifiedCardinality" );
    
    /** <p>The property that determines that two given individuals are equal.</p> */
    public static final OntProperty sameAs = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#sameAs" );
    
    /** <p>The property that determines the class that an existential property restriction 
     *  refers to.</p>
     */
    public static final OntProperty someValuesFrom = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#someValuesFrom" );
    
    /** <p>The property that determines the subject of a negative property assertion.</p> */
    public static final OntProperty sourceIndividual = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#sourceIndividual" );
    
    /** <p>The property that determines the object of a negative object property assertion.</p> */
    public static final OntProperty targetIndividual = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#targetIndividual" );
    
    /** <p>The property that determines the value of a negative data property assertion.</p> */
    public static final OntProperty targetValue = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#targetValue" );
    
    /** <p>The property that determines the collection of classes or data ranges that 
     *  build a union.</p>
     */
    public static final OntProperty unionOf = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#unionOf" );
    
    /** <p>The property that determines the collection of facet-value pairs that define 
     *  a datatype restriction.</p>
     */
    public static final OntProperty withRestrictions = m_model.createOntProperty( "http://www.w3.org/2002/07/owl#withRestrictions" );
    
    /** <p>This is the empty class.</p> */
    public static final OntClass Nothing = m_model.createClass( "http://www.w3.org/2002/07/owl#Nothing" );
    
    /** <p>The class of OWL individuals.</p> */
    public static final OntClass Thing = m_model.createClass( "http://www.w3.org/2002/07/owl#Thing" );
    
}
