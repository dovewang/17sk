package flint.business.web.view;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

public class FreeMarkerThemeViewResolver extends AbstractTemplateViewResolver {

	public FreeMarkerThemeViewResolver() {
		setViewClass(requiredViewClass());
	}

	/**
	 * Requires {@link FreeMarkerView}.
	 */
	@Override
	protected Class<FreeMarkerThemeView> requiredViewClass() {
		return FreeMarkerThemeView.class;
	}
}
