package com.bos.tis.tools.util;

public class Page
{
  private Integer itemsperpage;
  private Integer currentPage;
  private Integer totalItems;
  private Integer totalPages;

  public Integer getCurrentPage()
  {
    if (this.currentPage == null) {
      return Integer.valueOf(1);
    }
    return this.currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Integer getItemsperpage() {
    if (this.itemsperpage == null) {
      return Integer.valueOf(10);
    }
    return this.itemsperpage;
  }

  public void setItemsperpage(Integer itemsperpage) {
    this.itemsperpage = itemsperpage;
  }

  public Integer getTotalItems() {
    return this.totalItems;
  }

  public void setTotalItems(Integer totalItems) {
    this.totalItems = totalItems;
  }

  public Integer getTotalPages() {
    int a = this.totalItems.intValue() / getItemsperpage().intValue();
    int b = this.totalItems.intValue() % getItemsperpage().intValue();
    if (b > 0) {
      a += 1;
    }
    if (a == 0) {
      a = 1;
    }
    return Integer.valueOf(a);
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }
}