(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[434],{27483:function(e,t,r){"use strict";r.d(t,{e:function(){return c}});var n=r(92809),a=r(67294);function s(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function i(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?s(Object(r),!0).forEach((function(t){(0,n.Z)(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):s(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}var c=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{},t=(0,a.useState)(e),r=t[0],n=t[1];return[r,function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};n(i(i({},r),e))}]}},91534:function(e,t,r){"use strict";r.r(t),r.d(t,{default:function(){return y}});var n=r(92809),a=r(30266),s=r(66311),i=r(809),c=r.n(i),o=r(41664),l=r(67294),u=r(27483),d=r(40889),f=r(27966),p=r(86455),m=r.n(p),h=r(11163),v=r(75676),b=r(85893);function x(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function g(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?x(Object(r),!0).forEach((function(t){(0,n.Z)(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):x(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}var j={itemref:"",label:"",itemcode:"",price:"0.00",netprice:"0.00",counts:"0",isinfinite:!1,remaining:"0",tag:"",remark:""};function y(){var e=(0,h.useRouter)(),t=(0,l.useState)(!1),r=t[0],n=t[1],i=(0,u.e)(j),p=(0,s.Z)(i,2),x=p[0],y=p[1],w=function(){var e=(0,a.Z)(c().mark((function e(t){var r,n,a,i;return c().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,f.Z.get("/inventory-activities/remainings/".concat(t));case 2:return r=e.sent,n=(0,s.Z)(r,2),a=n[0],(i=n[1])&&(0,v.x)(i),e.abrupt("return",a.data.data);case 8:case"end":return e.stop()}}),e)})));return function(t){return e.apply(this,arguments)}}();(0,l.useEffect)((function(){var e=localStorage.getItem("itemref");e&&(m().fire({showConfirmButton:!1,title:"Please Wait !",html:'<div style="width: 5rem; height: 5rem;" class="spinner-border m-3 text-info" role="status">\n                <span class="visually-hidden">Loading...</span>\n              </div>',allowOutsideClick:!1,onBeforeOpen:function(){m().showLoading()}}),f.Z.get("/inventories/".concat(e)).then((function(e){var t=(0,s.Z)(e,2),r=t[0],n=t[1];m().close(),n?m().fire({icon:"error",title:"Oops...",text:n.message,footer:'<a href="">Why do I have this issue?</a>'}):(r.data.data.price=(0,d.l)(r.data.data.price),y(r.data.data))})))}),[]);var O=function(){var e=(0,a.Z)(c().mark((function e(){var t,a,i,o,l,u,p,h,v;return c().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:if(!r){e.next=2;break}return e.abrupt("return");case 2:return n(!0),m().fire({showConfirmButton:!1,title:"Please Wait !",html:'<div style="width: 5rem; height: 5rem;" class="spinner-border m-3 text-info" role="status">\n          <span class="visually-hidden">Loading...</span>\n        </div>',allowOutsideClick:!1,onBeforeOpen:function(){m().showLoading()}}),console.log(x),t=g(g({},x),{},{price:x.price.replaceAll(",","")}),(i=localStorage.getItem("itemref"))?a=f.Z.put("/inventories/".concat(i),t):(t.hasOwnProperty("id")&&delete t.id,a=f.Z.post("/inventories",t)),e.next=10,a;case 10:if(o=e.sent,l=(0,s.Z)(o,2),u=l[0],(p=l[1])&&(m().close(),m().fire({icon:"error",title:"Oops...",text:p.message,footer:'<a href="">Why do I have this issue?</a>'})),!u){e.next=31;break}if(m().close(),console.log(u),m().fire({position:"center",icon:"success",title:u.data.message,showConfirmButton:!1,timer:5e3}),i){e.next=27;break}return e.next=22,w(u.data.data.itemref);case 22:h=e.sent,y(g(g({},u.data.data),{},{price:(0,d.l)(u.data.data.price),remaining:h})),localStorage.setItem("itemref",u.data.data.itemref),e.next=31;break;case 27:return e.next=29,w(i);case 29:v=e.sent,y({remaining:v});case 31:n(!1);case 32:case"end":return e.stop()}}),e)})));return function(){return e.apply(this,arguments)}}();return(0,b.jsxs)("div",{children:[(0,b.jsx)("nav",{style:{"--bs-breadcrumb-divider":"'>'"},"aria-label":"breadcrumb",children:(0,b.jsxs)("ol",{className:"breadcrumb",children:[(0,b.jsx)("li",{class:"breadcrumb-item",children:(0,b.jsx)(o.default,{href:"/inventory",children:(0,b.jsx)("a",{children:"Inventory"})})}),(0,b.jsx)("li",{className:"breadcrumb-item active","aria-current":"page",children:x.itemref?x.itemref:"New"})]})}),(0,b.jsx)("div",{className:"card",children:(0,b.jsxs)("div",{className:"card-body",children:[(0,b.jsxs)("div",{className:"row mb-4",children:[(0,b.jsxs)("div",{className:"col-xl-4",children:[(0,b.jsx)("button",{className:"btn btn-primary mx-2",style:{marginLeft:"0px!important"},onClick:function(){localStorage.setItem("itemref",""),y(j)},children:"New"}),(0,b.jsx)("button",{disabled:!x.label||!x.itemcode||!x.price,className:"btn btn-success mx-2",onClick:O,children:x.itemref?"Update":"Save"}),(0,b.jsx)("button",{disabled:!x.itemref,onClick:function(t){var r=m().mixin({customClass:{confirmButton:"btn btn-success mx-2",cancelButton:"btn btn-danger mx-2"},buttonsStyling:!1});r.fire({title:"Are you sure?",text:"You won't be able to revert this!",icon:"warning",showCancelButton:!0,confirmButtonText:"Yes, delete it!",cancelButtonText:"No, cancel!",reverseButtons:!0}).then((function(n){n.isConfirmed?(m().fire({showConfirmButton:!1,title:"Please Wait !",html:'<div style="width: 5rem; height: 5rem;" class="spinner-border m-3 text-info" role="status">\n                    <span class="visually-hidden">Loading...</span>\n                  </div>',allowOutsideClick:!1,onBeforeOpen:function(){m().showLoading()}}),f.Z.delete("/inventories/".concat(t)).then((function(t){var n=(0,s.Z)(t,2),a=(n[0],n[1]);m().close(),a?m().fire({icon:"error",title:"Oops...",text:a.message,footer:'<a href="">Why do I have this issue?</a>'}):r.fire("Deleted!","Your data has been deleted.","success").then((function(){e.push("/inventory")}))}))):n.dismiss===m().DismissReason.cancel&&r.fire("Cancelled","Your data is safe :)","error")}))}.bind(this,x.itemref),className:"btn btn-danger mx-2",children:"Delete"})]}),(0,b.jsx)("div",{className:"col-xl-1"}),(0,b.jsx)("div",{className:"col-xl-1"})]}),(0,b.jsx)("div",{className:"row mb-3",children:(0,b.jsxs)("div",{className:"col-xl-3",children:[(0,b.jsx)("label",{className:"form-label",children:"Ref."}),(0,b.jsx)("input",{type:"text",readOnly:!0,className:"form-control",value:x.itemref,onChange:function(e){return y({itemref:e.target.value})}})]})}),(0,b.jsxs)("div",{className:"row mb-3",children:[(0,b.jsxs)("div",{className:"col-xl-3",children:[(0,b.jsx)("label",{className:"form-label",children:"Label"}),(0,b.jsx)("input",{type:"text",className:"form-control required-field",value:x.label,onChange:function(e){return y({label:e.target.value})}})]}),(0,b.jsxs)("div",{className:"col-xl-3",children:[(0,b.jsx)("label",{className:"form-label",children:"Inventory Code"}),(0,b.jsx)("input",{type:"text",className:"form-control required-field",value:x.itemcode,onChange:function(e){return y({itemcode:e.target.value})}})]})]}),(0,b.jsxs)("div",{className:"row mb-3",style:{alignItems:"flex-end"},children:[(0,b.jsxs)("div",{className:"col-xl-2",children:[(0,b.jsx)("label",{className:"form-label",children:"Price"}),(0,b.jsx)("input",{value:x.price,type:"text",className:"form-control price-control required-field",onChange:function(e){return y({price:e.target.value.replace(/[a-zA-Z]/g,"")})},onBlur:function(e){return y({price:(0,d.l)(e.target.value)})}})]}),(0,b.jsxs)("div",{className:"col-xl-2",children:[(0,b.jsx)("label",{className:"form-label",children:"Initial Counts"}),(0,b.jsxs)("div",{class:"input-group",style:{borderRadius:"10px"},children:[(0,b.jsx)("span",{class:"input-group-text",id:"basic-addon1",style:{borderRadius:"10px 0 0 10px",background:"#fff"},onClick:function(){var e=parseInt(x.counts||"0");e>0&&y({counts:e-1})},children:(0,b.jsx)("svg",{style:{width:"1rem"},"aria-hidden":"true",focusable:"false","data-prefix":"fal","data-icon":"minus",role:"img",xmlns:"http://www.w3.org/2000/svg",viewBox:"0 0 384 512",class:"svg-inline--fa fa-minus fa-w-12 fa-3x",children:(0,b.jsx)("path",{fill:"currentColor",d:"M376 232H8c-4.42 0-8 3.58-8 8v32c0 4.42 3.58 8 8 8h368c4.42 0 8-3.58 8-8v-32c0-4.42-3.58-8-8-8z",class:""})})}),(0,b.jsx)("input",{type:"text",class:"form-control","aria-describedby":"basic-addon1",style:{textAlign:"center"},value:x.counts,onChange:function(e){return y({counts:e.target.value.replace(/[^0-9]/g,"")})}}),(0,b.jsx)("span",{class:"input-group-text",id:"basic-addon1",style:{borderRadius:"0 10px 10px 0",background:"#fff"},onClick:function(){return y({counts:parseInt(x.counts||0)+1})},children:(0,b.jsx)("svg",{style:{width:"1rem"},"aria-hidden":"true",focusable:"false","data-prefix":"fal","data-icon":"plus",role:"img",xmlns:"http://www.w3.org/2000/svg",viewBox:"0 0 384 512",class:"svg-inline--fa fa-plus fa-w-12 fa-3x",children:(0,b.jsx)("path",{fill:"currentColor",d:"M376 232H216V72c0-4.42-3.58-8-8-8h-32c-4.42 0-8 3.58-8 8v160H8c-4.42 0-8 3.58-8 8v32c0 4.42 3.58 8 8 8h160v160c0 4.42 3.58 8 8 8h32c4.42 0 8-3.58 8-8V280h160c4.42 0 8-3.58 8-8v-32c0-4.42-3.58-8-8-8z",class:""})})})]})]}),(0,b.jsx)("div",{className:"col-xl-2",children:(0,b.jsxs)("div",{class:"btn-group",role:"group","aria-label":"Basic radio toggle button group",onChange:function(e){return y({isinfinite:"1"==e.target.value})},children:[(0,b.jsx)("input",{type:"radio",class:"btn-check",name:"btnradio",id:"btnradio1",autocomplete:"off",value:"0",checked:!x.isinfinite}),(0,b.jsx)("label",{class:"btn btn-outline-primary",for:"btnradio1",children:"Finite"}),(0,b.jsx)("input",{type:"radio",class:"btn-check",name:"btnradio",id:"btnradio2",autocomplete:"off",value:"1",checked:x.isinfinite}),(0,b.jsx)("label",{class:"btn btn-outline-primary",for:"btnradio2",children:"Infinite"})]})})]}),(0,b.jsx)("div",{className:"row mb-3",children:(0,b.jsxs)("div",{className:"col-xl-2",children:[(0,b.jsx)("label",{className:"form-label",children:"Remaining"}),(0,b.jsx)("input",{type:"text",className:"form-control",readOnly:!0,value:x.remaining})]})}),(0,b.jsx)("div",{className:"row mb-3",children:(0,b.jsxs)("div",{className:"col-xl-3",children:[(0,b.jsx)("label",{className:"form-label",children:"Tag"}),(0,b.jsx)("input",{type:"text",className:"form-control",value:x.tag,onChange:function(e){return y({tag:e.target.value})}})]})}),(0,b.jsx)("div",{className:"row mb-3",children:(0,b.jsxs)("div",{className:"col-xl-5",children:[(0,b.jsx)("label",{className:"form-label",children:"Remark"}),(0,b.jsx)("textarea",{className:"form-control",value:x.remark,onChange:function(e){return y({remark:e.target.value})}})]})})]})})]})}},75676:function(e,t,r){"use strict";r.d(t,{x:function(){return s}});var n=r(86455),a=r.n(n),s=function(e){return a().fire({icon:"error",title:"Oops...",text:e.message,footer:'<a href="">Why do I have this issue?</a>'})}},40889:function(e,t,r){"use strict";r.d(t,{l:function(){return s}});var n=r(69174),a=r.n(n),s=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"0",t=parseFloat(e.toString().replaceAll(",","").replace(/[a-zA-Z]/g,"")||"0");return a()(t.toFixed(2))}},27966:function(e,t,r){"use strict";r.d(t,{Z:function(){return p}});var n=r(92809),a=r(30266),s=r(809),i=r.n(s),c=r(9669),o=r.n(c),l=JSON.parse('{"n":"http://localhost:8080/api/v1"}');function u(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function d(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?u(Object(r),!0).forEach((function(t){(0,n.Z)(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):u(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}var f=function(){return{Authorization:"Bearer ".concat(localStorage.getItem("token"))}},p={post:function(){var e=(0,a.Z)(i().mark((function e(t,r){var n,a,s=arguments;return i().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return n=s.length>2&&void 0!==s[2]?s[2]:{},e.prev=1,e.next=4,o().post(l.n+t,r,{headers:d(d({},f()),n)});case 4:return a=e.sent,e.abrupt("return",[a,null]);case 8:return e.prev=8,e.t0=e.catch(1),console.error(e.t0),e.abrupt("return",[null,e.t0]);case 12:case"end":return e.stop()}}),e,null,[[1,8]])})));return function(t,r){return e.apply(this,arguments)}}(),get:function(){var e=(0,a.Z)(i().mark((function e(t){var r,n,a=arguments;return i().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return r=a.length>1&&void 0!==a[1]?a[1]:{},e.prev=1,e.next=4,o().get(l.n+t,{headers:d(d({},f()),r)});case 4:return n=e.sent,e.abrupt("return",[n,null]);case 8:return e.prev=8,e.t0=e.catch(1),console.error(e.t0),e.abrupt("return",[null,e.t0]);case 12:case"end":return e.stop()}}),e,null,[[1,8]])})));return function(t){return e.apply(this,arguments)}}(),put:function(){var e=(0,a.Z)(i().mark((function e(t,r){var n,a,s=arguments;return i().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return n=s.length>2&&void 0!==s[2]?s[2]:{},e.prev=1,e.next=4,o().put(l.n+t,r,{headers:d(d({},f()),n)});case 4:return a=e.sent,e.abrupt("return",[a,null]);case 8:return e.prev=8,e.t0=e.catch(1),console.error(e.t0),e.abrupt("return",[null,e.t0]);case 12:case"end":return e.stop()}}),e,null,[[1,8]])})));return function(t,r){return e.apply(this,arguments)}}(),delete:function(){var e=(0,a.Z)(i().mark((function e(t){var r,n,a,s=arguments;return i().wrap((function(e){for(;;)switch(e.prev=e.next){case 0:return r=s.length>1&&void 0!==s[1]?s[1]:{},n=s.length>2&&void 0!==s[2]?s[2]:{},e.prev=2,e.next=5,o().delete(l.n+t,r,{headers:d(d({},f()),n)});case 5:return a=e.sent,e.abrupt("return",[a,null]);case 9:return e.prev=9,e.t0=e.catch(2),console.error(e.t0),e.abrupt("return",[null,e.t0]);case 13:case"end":return e.stop()}}),e,null,[[2,9]])})));return function(t){return e.apply(this,arguments)}}()}},4769:function(e,t,r){(window.__NEXT_P=window.__NEXT_P||[]).push(["/inventory/form",function(){return r(91534)}])}},function(e){e.O(0,[838,774,888,179],(function(){return t=4769,e(e.s=t);var t}));var t=e.O();_N_E=t}]);