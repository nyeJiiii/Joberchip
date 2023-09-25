package kr.joberchip.server.v1.share.page.dto.response;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * {
 *     pageId : MAIN_PAGE_ID,
 *     title : "main page title",
 *     children : [
 *        {
 *            pageId : CHILD_PAGE_1_ID,
 *            title : "child page 1 title",
 *            children : [
 *              { ... }
 *            ]
 *        },
 *        {
 *            pageId : CHILD_PAGE_2_ID,
 *            title : "child page 2 title",
 *            children : [
 *              { ... }
 *            ]
 *        }
 *     ]
 * }
 */
public class PageTreeResponse {
  public record PageTreeNode(UUID pageId, String title, Set<PageTreeNode> childNodes) {
    public void addChildNote(PageTreeNode child) {
      childNodes.add(child);
    }

    public static PageTreeNode of(UUID pageId, String title) {
      return new PageTreeNode(pageId, title, new LinkedHashSet<>());
    }
  }
}
