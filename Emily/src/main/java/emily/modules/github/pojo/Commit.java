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

package emily.modules.github.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Commit {

    @SerializedName("author")
    @Expose
    private AuthorShort authorShort;
    @SerializedName("committer")
    @Expose
    private CommitterShort committerShort;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("tree")
    @Expose
    private Tree tree;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;

    /**
     * @return The author
     */
    public AuthorShort getAuthorShort() {
        return authorShort;
    }

    /**
     * @param authorShort The author
     */
    public void setAuthorShort(AuthorShort authorShort) {
        this.authorShort = authorShort;
    }

    /**
     * @return The committer
     */
    public CommitterShort getCommitterShort() {
        return committerShort;
    }

    /**
     * @param committerShort The committer
     */
    public void setCommitterShort(CommitterShort committerShort) {
        this.committerShort = committerShort;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The tree
     */
    public Tree getTree() {
        return tree;
    }

    /**
     * @param tree The tree
     */
    public void setTree(Tree tree) {
        this.tree = tree;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The commentCount
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * @param commentCount The comment_count
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

}
