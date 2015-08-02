package cat.santi.mod.interaction;

import cat.santi.ttfe.core.interaction.UseCaseResult;

/**
 * @author Santiago Gonzalez
 */
public class UseCaseResultImpl implements UseCaseResult {

    private boolean mSuccess;

    @Override
    public boolean isSuccess() {

        return mSuccess;
    }

    public static class Builder {

        private boolean mSuccess;

        public Builder setSuccess(boolean success) {

            mSuccess = success;
            return this;
        }

        public UseCaseResult build() {

            UseCaseResultImpl result = new UseCaseResultImpl();
            result.mSuccess = mSuccess;
            return result;
        }
    }
}
