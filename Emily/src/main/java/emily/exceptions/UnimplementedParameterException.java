/*
 * Copyright 2017 github.com/kaaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package emily.exceptions;

import java.sql.SQLException;

public class UnimplementedParameterException extends SQLException {

    private String s;

    public UnimplementedParameterException(Object parameter) {
        s = "Parameter not implemented at for: " + parameter;
    }

    public UnimplementedParameterException(Object parameter, int pos) {
        s = "Parameter not implemented! parameter:" + parameter + " - position:" + pos;
    }

    @Override
    public String toString() {
        return s;
    }
}
