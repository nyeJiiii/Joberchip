package kr.joberchip.server.v1.domain.page.controller.dto;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * { pageId : MAIN_PAGE_ID, title : "main page title", children : [ { pageId : CHILD_PAGE_1_ID,
 * title : "child page 1 title", children : [ { ... } ] }, { pageId : CHILD_PAGE_2_ID, title :
 * "child page 2 title", children : [ { ... } ] } ] }
 */
public class SharePageTreeResponseDTO {
  public record PageTreeNode(UUID parentId, UUID pageId, String title, Set<PageTreeNode> children) {
    public void addChild(PageTreeNode child) {
      children.add(child);
    }

    public static PageTreeNode of(UUID parentId, UUID pageId, String title) {
      return new PageTreeNode(parentId, pageId, title, new LinkedHashSet<>());
    }

    public static PageTreeNode of(
        UUID parentId, UUID pageId, String title, Set<PageTreeNode> children) {
      return new PageTreeNode(parentId, pageId, title, children);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      PageTreeNode that = (PageTreeNode) o;
      return Objects.equals(pageId, that.pageId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(pageId);
    }
  }
}
