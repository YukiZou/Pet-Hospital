webpackJsonp([1],{"04x+":function(e,t){},"BM+Q":function(e,t){},ENLC:function(e,t){},NHnr:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var i=r("MVMM"),s={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},staticRenderFns:[]};var n=r("Z0/y")({name:"App"},s,!1,function(e){r("VU9I")},null,null).exports,a=r("zO6J"),o={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"hello"},[r("h1",[e._v(e._s(e.msg))]),e._v(" "),r("h2",[e._v("Essential Links")]),e._v(" "),e._m(0),e._v(" "),r("h2",[e._v("Ecosystem")]),e._v(" "),e._m(1)])},staticRenderFns:[function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("ul",[r("li",[r("a",{attrs:{href:"https://vuejs.org",target:"_blank"}},[e._v("\n        Core Docs\n      ")])]),e._v(" "),r("li",[r("a",{attrs:{href:"https://forum.vuejs.org",target:"_blank"}},[e._v("\n        Forum\n      ")])]),e._v(" "),r("li",[r("a",{attrs:{href:"https://chat.vuejs.org",target:"_blank"}},[e._v("\n        Community Chat\n      ")])]),e._v(" "),r("li",[r("a",{attrs:{href:"https://twitter.com/vuejs",target:"_blank"}},[e._v("\n        Twitter\n      ")])]),e._v(" "),r("br"),e._v(" "),r("li",[r("a",{attrs:{href:"http://vuejs-templates.github.io/webpack/",target:"_blank"}},[e._v("\n        Docs for This Template\n      ")])])])},function(){var e=this.$createElement,t=this._self._c||e;return t("ul",[t("li",[t("a",{attrs:{href:"http://router.vuejs.org/",target:"_blank"}},[this._v("\n        vue-router\n      ")])]),this._v(" "),t("li",[t("a",{attrs:{href:"http://vuex.vuejs.org/",target:"_blank"}},[this._v("\n        vuex\n      ")])]),this._v(" "),t("li",[t("a",{attrs:{href:"http://vue-loader.vuejs.org/",target:"_blank"}},[this._v("\n        vue-loader\n      ")])]),this._v(" "),t("li",[t("a",{attrs:{href:"https://github.com/vuejs/awesome-vue",target:"_blank"}},[this._v("\n        awesome-vue\n      ")])])])}]};var l=r("Z0/y")({name:"HelloWorld",data:function(){return{msg:"Welcome to Your Vue.js App"}}},o,!1,function(e){r("04x+")},"data-v-694cd902",null).exports,u={props:{name:String,auth:String},data:function(){return{activeIndex:"",localname:this.name}},created:function(){this.activeIndex=this.$router.currentRoute.fullPath.split("/").pop()},methods:{handleSelect:function(e,t){this.$router.push({path:"/"+e})}}},c={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("el-header",[r("el-row",{attrs:{gutter:5}},[r("el-col",{attrs:{span:6}},[r("div",{staticStyle:{"text-align":"left"}},[r("big",[e._v("宠物医院学习系统")]),e._v(" "),r("small",[e._v("后台管理")])],1)]),e._v(" "),r("el-col",{attrs:{span:12}},[r("el-menu",{staticClass:"el-menu-demo",attrs:{"default-active":e.activeIndex,mode:"horizontal"},on:{select:e.handleSelect}},[r("el-menu-item",{attrs:{index:""}},[e._v("首页")]),e._v(" "),r("el-menu-item",{attrs:{index:"user"}},[e._v("用户管理")]),e._v(" "),r("el-menu-item",{attrs:{index:"question"}},[e._v("试题管理")]),e._v(" "),r("el-menu-item",{attrs:{index:"procedure"}},[e._v("流程管理")]),e._v(" "),r("el-menu-item",{attrs:{index:"department"}},[e._v("科室管理")]),e._v(" "),r("el-menu-item",{attrs:{index:"case"}},[e._v("病例管理")])],1),e._v(" "),r("div",{staticClass:"line"})],1),e._v(" "),r("el-col",{attrs:{span:6}},[r("div",{staticStyle:{"text-align":"right"}},[e._v("\n        用户："+e._s(e.name)+"\n      ")])])],1)],1)},staticRenderFns:[]};var d=r("Z0/y")(u,c,!1,function(e){r("xAh4")},null,null).exports,p={components:{myHeader:d},data:function(){return{name:"xxx",auth:0}},created:function(){this.name=this.getCookie("name"),this.auth=this.getCookie("auth"),this.userValidate()},methods:{userValidate:function(){"2"!==this.auth&&"3"!==this.auth&&this.$router.push({path:"/login"})},getCookie:function(e){var t,r=new RegExp("(^| )"+e+"=([^;]*)(;|$)");return(t=document.cookie.match(r))?unescape(t[2]):null}}},h={render:function(){var e=this.$createElement,t=this._self._c||e;return t("div",[t("my-header",{attrs:{name:this.name,auth:this.auth}}),this._v(" "),t("router-view")],1)},staticRenderFns:[]},m=r("Z0/y")(p,h,!1,null,null,null).exports,f={components:{myHeader:d},data:function(){return{loginForm:{name:"",pwd:""},loginRule:{name:[{required:!0,message:"请输入用户名",trigger:"blur"}],pwd:[{required:!0,message:"请输入密码",trigger:"blur"}]}}},methods:{setCookie:function(e,t){document.cookie=e+"="+escape(t)+";max-age=360"},submit:function(){var e=this;this.$refs.loginForm.validate(function(t){t&&e.$api.post("/user/login",{userName:e.loginForm.name,pwd:e.loginForm.pwd},function(t){"success"===t.status&&1!==t.auth?(e.setCookie("name",t.userName),e.setCookie("auth",t.auth),e.setCookie("picUrl",t.pictureUrl),e.$router.push({path:"/"})):1===t.auth?e.$notify.error({title:"错误",message:"用户权限不够"}):e.$notify.error({title:"错误",message:"用户名或密码错误"})})})},reset:function(){this.$refs.loginForm.resetFields()}}},v={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("el-container",[r("el-header",[r("big",[e._v("欢迎来到 宠物医院学习系统")]),e._v(" "),r("small",[e._v("后台管理")])],1),e._v(" "),r("el-main",[r("el-row",{attrs:{gutter:20}},[r("el-col",{attrs:{span:8}},[r("div",{staticClass:"grid-content"})]),e._v(" "),r("el-col",{staticClass:"grid-content",attrs:{span:8}},[r("el-form",{ref:"loginForm",attrs:{model:e.loginForm,"label-width":"80px",size:"small",rules:e.loginRule,"label-position":"top"}},[r("el-form-item",{attrs:{label:"用户名",prop:"name"}},[r("el-input",{attrs:{clearable:""},model:{value:e.loginForm.name,callback:function(t){e.$set(e.loginForm,"name",t)},expression:"loginForm.name"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"密码",prop:"pwd"}},[r("el-input",{attrs:{clearable:""},model:{value:e.loginForm.pwd,callback:function(t){e.$set(e.loginForm,"pwd",t)},expression:"loginForm.pwd"}})],1),e._v(" "),r("el-form-item",{attrs:{size:"medium"}},[r("el-col",{attrs:{span:8}},[e._v(" ")]),e._v(" "),r("el-button",{attrs:{type:"primary"},on:{click:e.submit}},[e._v("登录")]),e._v(" "),r("el-button",{on:{click:e.reset}},[e._v("重置")])],1)],1)],1)],1)],1)],1)},staticRenderFns:[]};var g=r("Z0/y")(f,v,!1,function(e){r("z4FQ")},null,null).exports,_={render:function(){var e=this.$createElement;return(this._self._c||e)("p",[this._v("请使用顶部导航栏前进到相应模块中")])},staticRenderFns:[]},b=r("Z0/y")(null,_,!1,null,null,null).exports,w={data:function(){return{auth:0,authFilter:0,nameKeyword:"",userList:[],userAuthOptions:[],operatingUser:{id:"",userName:"",pwd:"",auth:1,newPwd:"",newAuth:1},addUserDialogVisible:!1,modifyPwdDialogVisible:!1,modifyAuthDialogVisible:!1,removeUserDialogVisible:!1,currentPage:1,pageSize:5,addUserRule:{userName:[{required:!0,message:"请输入用户名",trigger:"blur"}],pwd:[{required:!0,message:"请输入密码",trigger:"blur"}],auth:[{required:!0,message:"请输入权限",trigger:"change"}]}}},created:function(){this.getAuth(),this.getUserList(),this.userAuthOptions=this.getUserAuthOptions()},computed:{filteredUserList:function(){return 0===this.authFilter?this.userList:this.userList.filter(function(e){return e.auth===this.authFilter}.bind(this))}},methods:{getAuth:function(){var e,t=new RegExp("(^| )auth=([^;]*)(;|$)");(e=document.cookie.match(t))&&(this.auth=parseInt(unescape(e[2])))},getUserList:function(){var e=this;this.$api.post("/user/filter",{userName:this.nameKeyword},function(t){"success"===t.status?e.userList=t.userList:e.$notify.error({title:"错误",message:"用户列表获取失败，请等待后重试"})})},isOperationDisabled:function(e,t){return 2===this.auth&&(1!==t||("modifyUserAuth"===e||void 0))},getUserAuthOptions:function(){return 3===this.auth?[{value:1,label:"User"},{value:2,label:"Admin"},{value:3,label:"Superadmin"}]:[{value:1,label:"User"}]},resetOperationUser:function(){this.operatingUser.id=0,this.operatingUser.userName="",this.operatingUser.auth=1,this.operatingUser.pwd="",this.operatingUser.newAuth=1,this.operatingUser.newPwd=""},addUser:function(){var e=this;this.$refs.addUserForm.validate(function(t){t&&e.$api.post("/user/add",{userName:e.operatingUser.userName,pwd:e.operatingUser.pwd,auth:e.operatingUser.auth},function(t){"success"===t.status?(e.userList.push({id:t.id,userName:t.userName,auth:t.auth}),e.$notify.success({title:"成功",message:"新用户ID为"+t.id}),e.$refs.addUserForm.resetFields(),e.resetOperationUser(),e.addUserDialogVisible=!1):e.$notify.error({title:"错误",message:"新增用户失败，请等待后重试"})})})},removeUser:function(){var e=this;this.$api.post("/user/delete",{id:this.operatingUser.id},function(t){"success"===t.status?(e.getUserList(),e.$notify.success({title:"成功",message:"原用户ID为"+e.operatingUser.id+"已被删除"}),e.resetOperationUser(),e.removeUserDialogVisible=!1):e.$notify.error({title:"错误",message:"删除用户失败，请等待后重试"})})},modifyUserPwd:function(){var e=this;this.$api.post("/user/pwd",{id:this.operatingUser.id,pwd:this.operatingUser.newPwd},function(t){"success"===t.status?(e.$notify.success({title:"成功",message:"已重置用户"+e.operatingUser.id+"的密码"}),e.resetOperationUser(),e.modifyPwdDialogVisible=!1):e.$notify.error({title:"错误",message:"重置用户密码失败，请等待后重试"})})},showModifyAuthDialog:function(e,t,r){this.modifyAuthDialogVisible=!0,this.operatingUser.id=e,this.operatingUser.userName=t,this.operatingUser.auth=r},showModifyPwdDialog:function(e,t){this.modifyPwdDialogVisible=!0,this.operatingUser.id=e,this.operatingUser.userName=t},modifyUserAuth:function(){var e=this;this.$api.post("/user/auth",{id:this.operatingUser.id,auth:this.operatingUser.newAuth},function(t){"success"===t.status?(e.getUserList(),e.$notify.success({title:"成功",message:"已修改用户"+e.operatingUser.id+"的权限"}),e.resetOperationUser(),e.modifyAuthDialogVisible=!1):e.$notify.error({title:"错误",message:"修改权限密码失败，请等待后重试"})})},showAuth:function(e){return 1===e?"User":2===e?"Admin":"Superadmin"},handleSizeChange:function(e){this.pageSize=e},handleCurrentChange:function(e){this.currentPage=e},authFormatter:function(e){return this.showAuth(e.auth)},authFilterHandler:function(e,t,r){return t[r.property]===e},showRemoveUserDialog:function(e,t){this.removeUserDialogVisible=!0,this.operatingUser.id=e,this.operatingUser.userName=t},authFilterChange:function(e){0===e.auth.length?this.authFilter=0:this.authFilter=e.auth[0]}}},U={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticStyle:{padding:"50px"}},[r("el-row",{staticStyle:{"margin-top":"20px"}},[r("el-col",{attrs:{span:24}},[r("el-breadcrumb",{attrs:{separator:"/"}},[r("el-breadcrumb-item",{attrs:{to:{path:"/"}}},[e._v("首页")]),e._v(" "),r("el-breadcrumb-item",[e._v("用户管理")])],1)],1)],1),e._v(" "),r("el-row",{staticStyle:{"margin-top":"20px"},attrs:{gutter:20}},[r("el-col",{attrs:{span:5}},[r("el-button",{attrs:{type:"primary",icon:"el-icon-plus"},on:{click:function(t){e.addUserDialogVisible=!e.addUserDialogVisible}}},[e._v("增加用户")])],1),e._v(" "),r("el-col",{attrs:{span:9}},[r("el-form",{attrs:{inline:!0}},[r("el-form-item",[r("el-input",{attrs:{placeholder:"用户名",clearable:""},model:{value:e.nameKeyword,callback:function(t){e.nameKeyword=t},expression:"nameKeyword"}})],1),e._v(" "),r("el-button",{attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.getUserList}},[e._v("搜索用户")])],1)],1),e._v(" "),r("el-col",{attrs:{span:10}},[r("el-pagination",{attrs:{background:"","current-page":e.currentPage,"page-sizes":[5,10],"page-size":e.pageSize,layout:"prev, pager, next, jumper, sizes, ->, total",total:e.filteredUserList.length},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)],1),e._v(" "),r("el-table",{attrs:{stripe:"",data:e.filteredUserList.slice((e.currentPage-1)*e.pageSize,e.currentPage*e.pageSize)},on:{"filter-change":e.authFilterChange}},[r("el-table-column",{attrs:{prop:"id",label:"ID"}}),e._v(" "),r("el-table-column",{attrs:{prop:"userName",label:"用户名"}}),e._v(" "),r("el-table-column",{attrs:{prop:"auth",label:"权限",formatter:e.authFormatter,filters:[{text:"User",value:1},{text:"Admin",value:2},{text:"Superadmin",value:3}],"filter-multiple":!1,"filter-placement":"bottom-start","filter-method":e.authFilterHandler,"column-key":"auth"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-tag",{attrs:{type:1===t.row.auth?"primary":2===t.row.auth?"success":"warning","close-transition":""}},[e._v(e._s(e.showAuth(t.row.auth)))])]}}])}),e._v(" "),r("el-table-column",{attrs:{label:"操作",fixed:"right",width:"350"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("el-button-group",[r("el-button",{attrs:{disabled:e.isOperationDisabled("modifyUserPwd",t.row.auth),type:"primary",size:"small",icon:"el-icon-edit"},on:{click:function(r){e.showModifyPwdDialog(t.row.id,t.row.userName)}}},[e._v("重置密码")]),e._v(" "),r("el-button",{attrs:{disabled:e.isOperationDisabled("modifyUserAuth",t.row.auth),type:"primary",size:"small",icon:"el-icon-edit-outline\n"},on:{click:function(r){e.showModifyAuthDialog(t.row.id,t.row.userName,t.row.auth)}}},[e._v("修改权限")]),e._v(" "),r("el-button",{attrs:{disabled:e.isOperationDisabled("removeUser",t.row.auth),type:"primary",size:"small",icon:"el-icon-delete"},on:{click:function(r){e.showRemoveUserDialog(t.row.id,t.row.userName)}}},[e._v("删除用户")])],1)]}}])})],1),e._v(" "),r("el-dialog",{attrs:{title:"重置密码",visible:e.modifyPwdDialogVisible,width:"30%"},on:{"update:visible":function(t){e.modifyPwdDialogVisible=t}}},[r("span",[e._v("重置用户名为"+e._s(e.operatingUser.userName)+"的用户密码为")]),e._v(" "),r("el-input",{attrs:{placeholder:"新密码"},model:{value:e.operatingUser.newPwd,callback:function(t){e.$set(e.operatingUser,"newPwd",t)},expression:"operatingUser.newPwd"}}),e._v(" "),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(t){e.modifyPwdDialogVisible=!1}}},[e._v("取消")]),e._v(" "),r("el-button",{attrs:{type:"primary"},on:{click:e.modifyUserPwd}},[e._v("确定")])],1)],1),e._v(" "),r("el-dialog",{attrs:{title:"修改权限",visible:e.modifyAuthDialogVisible,width:"30%"},on:{"update:visible":function(t){e.modifyAuthDialogVisible=t}}},[r("span",[e._v("修改用户名为"+e._s(e.operatingUser.userName)+"的用户权限由"+e._s(e.showAuth(e.operatingUser.auth))+"为")]),e._v(" "),r("el-select",{model:{value:e.operatingUser.newAuth,callback:function(t){e.$set(e.operatingUser,"newAuth",t)},expression:"operatingUser.newAuth"}},e._l(e.userAuthOptions,function(e){return r("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),e._v(" "),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(t){e.modifyAuthDialogVisible=!1}}},[e._v("取消")]),e._v(" "),r("el-button",{attrs:{type:"primary"},on:{click:e.modifyUserAuth}},[e._v("确定")])],1)],1),e._v(" "),r("el-dialog",{attrs:{title:"删除用户",visible:e.removeUserDialogVisible,width:"30%"},on:{"update:visible":function(t){e.removeUserDialogVisible=t}}},[r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(t){e.removeUserDialogVisible=!1}}},[e._v("取消")]),e._v(" "),r("el-button",{attrs:{type:"danger"},on:{click:e.removeUser}},[e._v("确定")])],1)]),e._v(" "),r("el-dialog",{attrs:{title:"重置密码",visible:e.modifyPwdDialogVisible,width:"30%"},on:{"update:visible":function(t){e.modifyPwdDialogVisible=t}}},[r("span",[e._v("重置用户名为"+e._s(e.operatingUser.userName)+"的用户密码为")]),e._v(" "),r("el-input",{attrs:{placeholder:"新密码"},model:{value:e.operatingUser.newPwd,callback:function(t){e.$set(e.operatingUser,"newPwd",t)},expression:"operatingUser.newPwd"}}),e._v(" "),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(t){e.modifyPwdDialogVisible=!1}}},[e._v("取消")]),e._v(" "),r("el-button",{attrs:{type:"primary"},on:{click:e.modifyUserPwd}},[e._v("确定")])],1)],1),e._v(" "),r("el-dialog",{attrs:{title:"增加用户",visible:e.addUserDialogVisible,width:"30%"},on:{"update:visible":function(t){e.addUserDialogVisible=t}}},[r("el-form",{ref:"addUserForm",attrs:{model:e.operatingUser,"label-width":"80px",size:"small",rules:e.addUserRule,"label-position":"left"}},[r("el-form-item",{attrs:{label:"用户名",prop:"userName"}},[r("el-input",{attrs:{clearable:""},model:{value:e.operatingUser.userName,callback:function(t){e.$set(e.operatingUser,"userName",t)},expression:"operatingUser.userName"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"密码",prop:"pwd"}},[r("el-input",{attrs:{clearable:""},model:{value:e.operatingUser.pwd,callback:function(t){e.$set(e.operatingUser,"pwd",t)},expression:"operatingUser.pwd"}})],1),e._v(" "),r("el-form-item",{attrs:{label:"权限",prop:"auth"}},[r("el-select",{model:{value:e.operatingUser.auth,callback:function(t){e.$set(e.operatingUser,"auth",t)},expression:"operatingUser.auth"}},e._l(e.userAuthOptions,function(e){return r("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})}))],1)],1),e._v(" "),r("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[r("el-button",{on:{click:function(t){e.addUserDialogVisible=!1}}},[e._v("取消")]),e._v(" "),r("el-button",{attrs:{type:"primary"},on:{click:e.addUser}},[e._v("确定")])],1)],1)],1)},staticRenderFns:[]};var y=r("Z0/y")(w,U,!1,function(e){r("BM+Q")},null,null).exports,k={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",[t("p",[this._v("试题管理")])])}]},x=r("Z0/y")(null,k,!1,null,null,null).exports,$={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",[t("p",[this._v("科室管理")])])}]},D=r("Z0/y")(null,$,!1,null,null,null).exports,F={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",[t("p",[this._v("流程管理")])])}]},A=r("Z0/y")(null,F,!1,null,null,null).exports,P={render:function(){this.$createElement;this._self._c;return this._m(0)},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",[t("p",[this._v("病例管理")])])}]},V=r("Z0/y")(null,P,!1,null,null,null).exports;i.default.use(a.a);var C=new a.a({routes:[{path:"/",component:m,children:[{path:"",component:b},{path:"user",component:y},{path:"question",component:x},{path:"department",component:D},{path:"procedure",component:A},{path:"case",component:V}]},{path:"/helloworld",name:"HelloWorld",component:l},{path:"/login",component:g}]}),E=r("wi/X"),N=r.n(E),S=(r("ENLC"),"/api"),z=r("aozt");function L(e){return{}.toString.call(e).match(/\s([a-zA-Z]+)/)[1].toLowerCase()}function R(e,t,r,i){r&&(r=function e(t){for(var r in t)null===t[r]&&delete t[r],"string"===L(t[r])?t[r]=t[r].trim():"object"===L(t[r])?t[r]=e(t[r]):"array"===L(t[r])&&(t[r]=e(t[r]));return t}(r)),z({method:e,url:t,data:"POST"===e||"PUT"===e?r:null,params:"GET"===e||"DELETE"===e?r:null,baseURL:S,withCredentials:!1}).then(function(e){i&&i(e.data)}).catch(function(e){var t=e.response;e&&window.alert("api error, HTTP CODE: "+t.status)})}var O={get:function(e,t,r){return R("GET",e,t,r)},post:function(e,t,r){return R("POST",e,t,r)},put:function(e,t,r){return R("PUT",e,t,r)},delete:function(e,t,r){return R("DELETE",e,t,r)}};i.default.prototype.$api=O,i.default.config.productionTip=!1,i.default.use(N.a),new i.default({el:"#app",router:C,components:{App:n},template:"<App/>"})},VU9I:function(e,t){},xAh4:function(e,t){},z4FQ:function(e,t){}},["NHnr"]);
//# sourceMappingURL=app.b850cf432956f03a69be.js.map