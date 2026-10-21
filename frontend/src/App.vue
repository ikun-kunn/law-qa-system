<script setup>
import { ref } from 'vue'
const API='http://localhost:8080/api', q=ref(''), query=ref(''), clauses=ref([]), answer=ref(null), feedback=ref(''), rating=ref(5)
const favorites=ref(JSON.parse(localStorage.getItem('law-favorites')||'[]'))
async function search(){const r=await fetch(`${API}/clauses?q=${encodeURIComponent(query.value)}`);clauses.value=await r.json()}
async function ask(){if(!q.value.trim())return;const r=await fetch(`${API}/ask`,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({question:q.value})});answer.value=await r.json()}
function toggle(c){const i=favorites.value.findIndex(x=>x.id===c.id);i<0?favorites.value.push(c):favorites.value.splice(i,1);localStorage.setItem('law-favorites',JSON.stringify(favorites.value))}
async function sendFeedback(){const r=await fetch(`${API}/feedback`,{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({question:q.value||query.value||'条文搜索',rating:Number(rating.value),comment:feedback.value})});feedback.value=r.ok?'感谢反馈':'提交失败，请检查后端';}
</script>
<template><main><header><div><small>教学演示 · 条文溯源</small><h1>法律知识问答</h1><p>用可追溯条文辅助理解法律概念</p></div><span class="badge">仅供学习</span></header>
<aside>免责声明：本系统为教学演示，不构成法律意见。条文、版本及效力状态均需以官方现行文本核验。</aside>
<section class="panel"><h2>条文搜索</h2><form @submit.prevent="search"><input v-model="query" placeholder="输入关键词，例如：要约、邀请、撤销"><button>搜索条文</button></form><article v-for="c in clauses" :key="c.id" class="clause"><div class="row"><strong>{{c.articleNumber}} · {{c.title}}</strong><button class="ghost" @click="toggle(c)">{{favorites.some(x=>x.id===c.id)?'★ 已收藏':'☆ 收藏'}}</button></div><p>{{c.content}}</p><small>{{c.documentTitle}} · {{c.documentVersion}} · {{c.verificationStatus}}</small><a :href="c.sourceUrl" target="_blank" rel="noreferrer">查看官方来源（打开后请核验） ↗</a></article><p v-if="query && !clauses.length" class="muted">没有找到匹配条文。</p></section>
<section class="panel"><h2>规则问答</h2><form @submit.prevent="ask"><input v-model="q" placeholder="例如：什么是要约？"><button>提交问题</button></form><div v-if="answer" class="answer"><strong>{{answer.refused?'暂无法回答':'参考答复'}}</strong><p>{{answer.answer}}</p><div v-for="c in answer.citations" :key="c.id" class="citation"><b>{{c.articleNumber}} · {{c.title}}</b><p>{{c.content}}</p><a :href="c.sourceUrl" target="_blank" rel="noreferrer">来源链接 ↗</a></div><small>{{answer.disclaimer}}</small></div></section>
<section v-if="favorites.length" class="panel"><h2>我的收藏</h2><p v-for="c in favorites" :key="c.id">{{c.articleNumber}} · {{c.title}}</p></section>
<section class="panel"><h2>评分与反馈</h2><form @submit.prevent="sendFeedback"><label>评分 <select v-model="rating"><option v-for="n in 5" :value="n">{{n}} 星</option></select></label><input v-model="feedback" placeholder="意见或纠错建议（选填）"><button>提交反馈</button></form><small class="muted">{{feedback && feedback!=='' && !feedback.startsWith('意见')?feedback:''}}</small></section>
<footer>演示数据：仅包含少量条文样例，需人工核验，不可替代律师或官方法律数据库。</footer></main></template>
