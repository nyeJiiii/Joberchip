package kr.joberchip.server.v1.share.block.controller.dto;

import kr.joberchip.core.share.block.LinkBlock;
import kr.joberchip.server.v1._errors.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class LinkBlockDTO {
  @Getter
  public static class Create {
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private String title;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private String link;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer x;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer y;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer height;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer width;

    public LinkBlock setEntity (LinkBlock linkBlock){
      linkBlock.modifyTitle(this.getTitle());
      linkBlock.modifyLink(this.getLink());
      linkBlock.setX(this.getX());
      linkBlock.setY(this.getY());
      linkBlock.setHeight(this.getHeight());
      linkBlock.setWidth(this.getWidth());
      return linkBlock;
    }
  }

  @Getter
  public static class Modify {
    private String title;
    private String link;
  }

  @Getter
  @NoArgsConstructor
  public static class ReturnVisible {
    private Boolean visible;

    public ReturnVisible(LinkBlock linkBlock) {
      this.visible = linkBlock.getVisible();
    }
  }

}
