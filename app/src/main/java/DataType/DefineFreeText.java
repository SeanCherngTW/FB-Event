package DataType;

public class DefineFreeText {
    //public enum DefineFreeTextType {FB_OFFICE_FANS_FREE_TEXT}
    public enum DefineFreeTextType {FB_OFFICE_FANS_FREE_TEXT("FB_OFFICE_FANS_FREE_TEXT");
        private final String text;
        //private final Class mappingClazz;
        private Class mappingClazz;

        /**
         * @param text
         */
        private DefineFreeTextType(final String text) {
            this.text = text;
            if(text.equals("FB_OFFICE_FANS_FREE_TEXT"))
            {
                try {
                    this.mappingClazz = Class.forName("DataType.FBOfficePostSimpleFreeText");
                }
                catch (Exception ex)
                {

                }
            }
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }

        public Class getMappingClazz() {
            return mappingClazz;
        }
    }
    public static final String FB_OFFICE_FANS_FREE_TEXT  = "FB_OFFICE_FANS_FREE_TEXT";
}
