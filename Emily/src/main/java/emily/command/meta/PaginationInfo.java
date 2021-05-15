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

package emily.command.meta;

import net.dv8tion.jda.core.entities.Guild;

public class PaginationInfo<E> {

    private final int maxPage;
    private final Guild guild;
    private int currentPage = 0;
    private E extraData;

    public PaginationInfo(int currentPage, int maxPage, Guild guild) {
        this(currentPage, maxPage, guild, null);
    }

    public PaginationInfo(int currentPage, int maxPage, Guild guild, E extra) {

        this.currentPage = currentPage;
        this.maxPage = maxPage;
        this.guild = guild;
        this.extraData = extra;
    }

    public void setExtraData(E data) {
        extraData = data;
    }

    public E getExtra() {
        return extraData;
    }

    public boolean previousPage() {
        if (currentPage > 1) {
            currentPage--;
            return true;
        }
        return false;
    }

    public boolean nextPage() {
        if (currentPage < maxPage) {
            currentPage++;
            return true;
        }
        return false;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public Guild getGuild() {
        return guild;
    }
}
