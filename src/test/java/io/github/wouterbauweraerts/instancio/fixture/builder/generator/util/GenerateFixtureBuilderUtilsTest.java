package io.github.wouterbauweraerts.instancio.fixture.builder.generator.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import io.github.wouterbauweraerts.instancio.fixture.builder.generator.exception.FixtureBuilderGenerationException;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GenerateFixtureBuilderUtilsTest {
    static final String UNABLE_TO_READ_ANNOTATION = "Unable to read @GenerateFixtureBuilder annotation details";
    static final String MISSING_VALUE = "Unable to read value from %s in @GenerateFixtureBuilder annotation";
    static final String NO_INSTANCIO_MODEL = "No Instancio model found";

    @InjectMocks
    GenerateFixtureBuilderUtils utils;
    @Mock
    ProcessingEnvironment processingEnv;

    @Mock
    Elements elementUtils;
    @Mock
    Types typeUtils;

    @BeforeEach
    void setUp() {
        when(processingEnv.getElementUtils()).thenReturn(elementUtils);
        when(processingEnv.getTypeUtils()).thenReturn(typeUtils);
    }

    @Test
    void extractAnnotationMirror_notFound() {
        Element element = mock(Element.class);

        when(elementUtils.getAllAnnotationMirrors(any())).thenReturn(List.of());

        assertThatThrownBy(() -> utils.extractAnnotationMirror(element))
                .isInstanceOf(FixtureBuilderGenerationException.class)
                .hasMessage(UNABLE_TO_READ_ANNOTATION);

        verify(elementUtils).getAllAnnotationMirrors(element);
    }

    @Test
    void extractAnnotationMirror() {
        Element element = mock(Element.class);

        AnnotationMirror expectedMirror = setupAnnotationMirror(GenerateFixtureBuilderUtils.GENERATE_FIXTURE_BUILDER_QUALIFIED_NAME);
        AnnotationMirror otherMirror = setupAnnotationMirror(Instancio.create(String.class));
        AnnotationMirror otherMirror2 = setupAnnotationMirror(Instancio.create(String.class));

        List<? extends AnnotationMirror> mirrors = List.of(
                otherMirror,
                expectedMirror,
                otherMirror2
        );

        doReturn(mirrors).when(elementUtils).getAllAnnotationMirrors(any());

        assertThat(utils.extractAnnotationMirror(element))
                .isSameAs(expectedMirror);

        verify(elementUtils).getAllAnnotationMirrors(element);
    }

    private AnnotationMirror setupAnnotationMirror(String type) {
        AnnotationMirror mirror = mock(AnnotationMirror.class);
        DeclaredType declaredType = mock(DeclaredType.class);

        when(mirror.getAnnotationType()).thenReturn(declaredType);
        when(declaredType.toString()).thenReturn(type);

        return mirror;
    }

    @Test
    void getType_emptyElementValuesSet() {
        AnnotationMirror mirror = mock(AnnotationMirror.class);
        String key = Instancio.create(String.class);

        doReturn(Map.of()).when(elementUtils).getElementValuesWithDefaults(any());

        assertThatThrownBy(() -> utils.getType(mirror, key))
                .isInstanceOf(FixtureBuilderGenerationException.class)
                .hasMessage(MISSING_VALUE.formatted(key));

        verify(elementUtils).getElementValuesWithDefaults(mirror);
    }

    @Test
    void getType_noMatchingKey() {
        AnnotationMirror mirror = mock(AnnotationMirror.class);
        String key = Instancio.create(String.class);

        doReturn(Map.ofEntries(setupElementValue(false, null))).when(elementUtils).getElementValuesWithDefaults(any());

        assertThatThrownBy(() -> utils.getType(mirror, key))
                .isInstanceOf(FixtureBuilderGenerationException.class)
                .hasMessage(MISSING_VALUE.formatted(key));

        verify(elementUtils).getElementValuesWithDefaults(mirror);
    }

    @Test
    void getType() {
        Element returnValue = mock(Element.class);
        AnnotationMirror mirror = mock(AnnotationMirror.class);
        String key = Instancio.create(String.class);
        TypeMirror annotationValue = mock(TypeMirror.class);

        doReturn(Map.ofEntries(setupElementValue(true, annotationValue))).when(elementUtils).getElementValuesWithDefaults(any());
        doReturn(returnValue).when(typeUtils).asElement(any(TypeMirror.class));

        assertThat(utils.getType(mirror, key)).isSameAs(returnValue);

        verify(typeUtils).asElement(annotationValue);
    }

    private Map.Entry<ExecutableElement, AnnotationValue> setupElementValue(boolean keyMatch, TypeMirror annotationValue) {
        ExecutableElement keyElement = mock(ExecutableElement.class);
        AnnotationValue value = mock(AnnotationValue.class);
        Name name = mock(Name.class);

        when(keyElement.getSimpleName()).thenReturn(name);
        when(name.contentEquals(anyString())).thenReturn(keyMatch);
        when(value.getValue()).thenReturn(annotationValue);

        return Map.entry(keyElement, value);
    }

    @Test
    void extractInstancioModel_noEnclosedElements() {
        Element element = mock(Element.class);

        when(element.getEnclosedElements()).thenReturn(List.of());

        assertThatThrownBy(() -> utils.extractInstancioModel(element))
                .isInstanceOf(FixtureBuilderGenerationException.class)
                .hasMessage(NO_INSTANCIO_MODEL);
    }

    @Test
    void extractInstancioModel_noModelFound() {
        Element element = mock(Element.class);
        Element enclosedElement = mock(Element.class);

        doReturn(List.of(
                enclosedElement,
                enclosedElement,
                enclosedElement
        )).when(element).getEnclosedElements();
        when(enclosedElement.getAnnotation(any())).thenReturn(null);

        assertThatThrownBy(() -> utils.extractInstancioModel(element))
                .isInstanceOf(FixtureBuilderGenerationException.class)
                .hasMessage(NO_INSTANCIO_MODEL);
    }

    @Test
    void extractInstancioModel() {
        Element element = mock(Element.class);
        Element enclosedElement = mock(Element.class);
        Element instancioModelElement = mock(Element.class);

        doReturn(List.of(
                enclosedElement,
                enclosedElement,
                instancioModelElement
        )).when(element).getEnclosedElements();
        when(enclosedElement.getAnnotation(any())).thenReturn(null);
        when(instancioModelElement.getAnnotation(any())).thenReturn(mock(Annotation.class));

        assertThat(utils.extractInstancioModel(element))
                .isSameAs(instancioModelElement);
    }

    @Test
    void getPackageElement() {
        Element element = mock(Element.class);
        PackageElement packageElement = mock(PackageElement.class);
        Name qualifiedName = mock(Name.class);
        String packageName = Instancio.create(String.class);

        when(elementUtils.getPackageOf(any())).thenReturn(packageElement);
        when(packageElement.getQualifiedName()).thenReturn(qualifiedName);
        when(qualifiedName.toString()).thenReturn(packageName);

        assertThat(utils.extractPackageName(element)).isEqualTo(packageName);

        verify(elementUtils).getPackageOf(element);
        verify(packageElement).getQualifiedName();
    }
}