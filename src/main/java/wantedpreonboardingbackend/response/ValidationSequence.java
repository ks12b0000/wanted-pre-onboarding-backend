package wantedpreonboardingbackend.response;

import wantedpreonboardingbackend.response.ValidationGroup.EmailGroup;
import wantedpreonboardingbackend.response.ValidationGroup.NotBlankGroup;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

import static wantedpreonboardingbackend.response.ValidationGroup.*;


@GroupSequence({Default.class, NotBlankGroup.class,  EmailGroup.class, PatternGroup.class, NotNullGroup.class})
public interface ValidationSequence {
}
