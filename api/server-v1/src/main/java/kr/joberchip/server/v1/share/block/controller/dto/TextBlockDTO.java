package kr.joberchip.server.v1.share.block.controller.dto;

import kr.joberchip.core.share.block.TextBlock;
import kr.joberchip.server.v1._errors.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class TextBlockDTO {

  @Getter
  public static class Create {
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private String content;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer x;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer y;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer height;
    @NotNull(message = ErrorMessage.NOT_EMPTY)
    private Integer width;

    public TextBlock setEntity (TextBlock textBlock){
      textBlock.setContent(this.content);
      textBlock.setX(this.getX());
      textBlock.setY(this.getY());
      textBlock.setHeight(this.getHeight());
      textBlock.setWidth(this.getWidth());
      return textBlock;
    }
  }

  @Getter
  public static class Modify {
    private String content;
  }

  @Getter
  @NoArgsConstructor
  public static class ReturnVisible {
    private Boolean visible;

    public ReturnVisible(TextBlock textBlock) {
      this.visible = textBlock.getVisible();
    }
  }
}
