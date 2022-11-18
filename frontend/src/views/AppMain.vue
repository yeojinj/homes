<template>
  <div>
    <div id="main-image">
      <b-card
        overlay
        img-src="https://picsum.photos/900/250/?image=3"
        img-alt="Card Image"
        text-variant="white"
        title="Where is my home"
        sub-title="내 집 구하기"
      >
        <b-card-text>
          <hr class="my-4" />
          <p>홈페이지 이용 관련 문의사항을 <b>QnA</b>에 남겨주세요.</p>
        </b-card-text>
      </b-card>
    </div>

    <div id="main-boards">
      <div id="news">
        <h2>부동산 관련 뉴스</h2>
        <div id="news-detail">
          <b-card
            title="수세권·공세권·역세권 등 멀티세권 갖춘 ‘알루어 반포‘ 입주"
            img-src="https://imgnews.pstatic.net/image/014/2022/11/16/0004928826_001_20221116091702188.jpg?type=w647"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2"
          >
            <b-card-text>
              주거 트렌드가 변화를 거듭하지만, 주택을 선택할 때 가장 먼저 고려하는 요소는 단연 ‘입지’임에는 변함이 없다.
              주택의 상품성은 건설사의 기술력이나 노하우, 고객들의 니즈 등을 반영해 어느 정도 개선할 수 있지만, ...
            </b-card-text>

            <b-button href="https://n.news.naver.com/mnews/article/014/0004928826?sid=101" variant="light"
              >기사 원문</b-button
            >
          </b-card>
        </div>
        <div id="news-detail">
          <b-card
            title="서울 한남4구역 재정비…최고 23층 2167세대"
            img-src="https://imgnews.pstatic.net/image/215/2022/11/16/A202211160053_1_20221116091701763.jpg?type=w647"
            img-alt="Image"
            img-top
            tag="article"
            style="max-width: 20rem"
            class="mb-2"
          >
            <b-card-text>
              서울 시내 재개발 지역 가운데 노른자 땅으로 꼽히는 한남4구역 재정비촉진계획이 서울시 심의를 통과했다. 최고
              23층 규모의 아파트 2천167세대가 들어서고, 기존 신동아아파트는 전면 재개발된다. ...
            </b-card-text>

            <b-button href="https://n.news.naver.com/mnews/article/215/0001065899?sid=101" variant="light"
              >기사 원문</b-button
            >
          </b-card>
        </div>
      </div>

      <!-- <div id="notice">
        <h2>공지사항</h2>
      </div> -->
    </div>
  </div>
</template>

<script>
export default {
  name: "AppMain",
  props: {
    msg: String,
  },
};
</script>

<style scoped>
#main-image {
  margin-top: 80px;
}

#main-boards {
  margin-top: 80px;
  margin-bottom: 80px;
  display: inline-block;
}

#news {
  display: inline-block;
}

#news-detail {
  float: left;
  margin: 10px;
}
</style>
