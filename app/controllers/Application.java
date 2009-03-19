package controllers;

import constants.MyConstants;
import org.apache.commons.lang.StringUtils;
import play.i18n.Lang;
import play.mvc.*;


public class Application extends Controller {

    @Before
    static void set_language() {

        String prefered_lang = session.get(MyConstants.PREFERED_LANG);

        if (StringUtils.isNotBlank(prefered_lang)) {
            Lang.change(prefered_lang);
        }

    }
}