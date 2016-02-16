// vim: ts=4:sw=4:nu:fdc=4:nospell
/**
 * Search plugin for Ext.grid.GridPanel, Ext.grid.EditorGrid ver. 2.x or
 * subclasses of them
 * 
 * @author Ing. Jozef Sakalos
 * @copyright (c) 2008, by Ing. Jozef Sakalos
 * @date 17. January 2008
 * @version $Id: Ext.ux.grid.Search.js 120 2008-03-31 00:09:05Z jozo $
 * 
 * @license Ext.ux.grid.Search is licensed under the terms of the Open Source
 *          LGPL 3.0 license. Commercial use is permitted to the extent that the
 *          code/component(s) do NOT become part of another Open Source or
 *          Commercially licensed development library or toolkit without
 *          explicit permission.
 * 
 * License details: http://www.gnu.org/licenses/lgpl.html
 */

/* global Ext */

Ext.ns('Ext.ux.grid');

/**
 * @class Ext.ux.grid.Search
 * @extends Ext.util.Observable
 * @param {Object}
 *            config configuration object
 * @constructor
 */
Ext.ux.grid.Search = function(config) {
    Ext.apply(this, config);
    Ext.ux.grid.Search.superclass.constructor.call(this);
}; // eo constructor

Ext.extend(Ext.ux.grid.Search, Ext.util.Observable, {
    /**
	 * @cfg {String} searchText Text to display on menu button
	 */
     searchText:'Search'

    /**
	 * @cfg {String} searchTipText Text to display as input tooltip. Set to ''
	 *      for no tooltip
	 */
    ,searchTipText:'Type a text to search and press Enter'

    /**
	 * @cfg {String} selectAllText Text to display on menu item that selects all
	 *      fields
	 */
    ,selectAllText:'Select All'

    /**
	 * @cfg {String} position Where to display the search controls. Valid values
	 *      are top and bottom (defaults to bottom) Corresponding toolbar has to
	 *      exist at least with mimimum configuration tbar:[] for position:top
	 *      or bbar:[] for position bottom. Plugin does NOT create any toolbar.
	 */
    ,position:'bottom'

    /**
	 * @cfg {String} iconCls Icon class for menu button (defaults to
	 *      icon-magnifier)
	 */
    ,iconCls:'icon-magnifier'

    /**
	 * @cfg {String/Array} checkIndexes Which indexes to check by default. Can
	 *      be either 'all' for all indexes or array of dataIndex names, e.g.
	 *      ['persFirstName', 'persLastName']
	 */
    ,checkIndexes:'all'

    /**
	 * @cfg {Array} disableIndexes Array of index names to disable (not show in
	 *      the menu), e.g. ['persTitle', 'persTitle2']
	 */
    ,disableIndexes:[]

    /**
	 * @cfg {String} dateFormat how to format date values. If undefined (the
	 *      default) date is formatted as configured in colummn model
	 */
    ,dateFormat:undefined

    /**
	 * @cfg {Boolean} showSelectAll Select All item is shown in menu if true
	 *      (defaults to true)
	 */
    ,showSelectAll:true

    /**
	 * @cfg {String} mode Use 'remote' for remote stores or 'local' for local
	 *      stores. If mode is local no data requests are sent to server the
	 *      grid's store is filtered instead (defaults to 'remote')
	 */
    ,mode:'remote'

    /**
	 * @cfg {Number} width Width of input field in pixels (defaults to 100)
	 */
    ,width:100

    /**
	 * @cfg {String} xtype xtype is usually not used to instantiate this plugin
	 *      but you have a chance to identify it
	 */
    ,xtype:'gridsearch'

    /**
	 * @cfg {Object} paramNames Params name map (defaults to {fields:'fields',
	 *      query:'query'}
	 */
    ,paramNames: {
         fields:'fields'
        ,query:'query'
    }

    /**
	 * @cfg {String} shortcutKey Key to fucus the input field (defaults to r =
	 *      Sea_r_ch). Empty string disables shortcut
	 */
    ,shortcutKey:'r'

    /**
	 * @cfg {String} shortcutModifier Modifier for shortcutKey. Valid values:
	 *      alt, ctrl, shift (defaults to alt)
	 */
    ,shortcutModifier:'alt'

    /**
	 * @cfg {String} align 'left' or 'right' (defaults to 'left')
	 */

    /**
	 * @cfg {Number} minLength force user to type this many character before he
	 *      can make a search
	 */

    /**
	 * @cfg {Ext.Panel/String} toolbarContainer Panel (or id of the panel) which
	 *      contains toolbar we want to render search controls to (defaults to
	 *      this.grid, the grid this plugin is plugged-in into)
	 */

    // {{{
    /**
	 * private
	 * 
	 * @param {Ext.grid.GridPanel/Ext.grid.EditorGrid}
	 *            grid reference to grid this plugin is used for
	 */
    ,init:function(grid) {
        this.grid = grid;

        // setup toolbar container if id was given
        if('string' === typeof this.toolbarContainer) {
            this.toolbarContainer = Ext.getCmp(this.toolbarContainer);
        }

        // do our processing after grid render and reconfigure
        grid.onRender = grid.onRender.createSequence(this.onRender, this);
        grid.reconfigure = grid.reconfigure.createSequence(this.reconfigure, this);
    } // eo function init
    // }}}
    // {{{
    /**
	 * private add plugin controls to <b>existing</b> toolbar and calls
	 * reconfigure
	 */
    ,onRender:function() {
        var panel = this.toolbarContainer || this.grid;
        var tb = 'bottom' === this.position ? panel.bottomToolbar : panel.topToolbar;

        // add menu
        this.menu = new Ext.menu.Menu();

        // handle position
        if('right' === this.align) {
            tb.addFill();
        }
        else {
            tb.addSeparator();
        }

        // add menu button
        tb.add({
             text:this.searchText
            ,menu:this.menu
            ,iconCls:this.iconCls
        });

        // add input field (TwinTriggerField in fact)
        this.field = new Ext.form.TwinTriggerField({
             width:this.width
            ,selectOnFocus:undefined === this.selectOnFocus ? true : this.selectOnFocus
            ,trigger1Class:'x-form-clear-trigger'
            ,trigger2Class:'x-form-search-trigger'
            ,onTrigger1Click:this.onTriggerClear.createDelegate(this)
            ,onTrigger2Click:this.onTriggerSearch.createDelegate(this)
            ,minLength:this.minLength
        });

        // install event handlers on input field
        this.field.on('render', function() {
            this.field.el.dom.qtip = this.searchTipText;

            // install key map
            var map = new Ext.KeyMap(this.field.el, [{
                 key:Ext.EventObject.ENTER
                ,scope:this
                ,fn:this.onTriggerSearch
            },{
                 key:Ext.EventObject.ESC
                ,scope:this
                ,fn:this.onTriggerClear
            }]);
            map.stopEvent = true;
        }, this, {single:true});

        tb.add(this.field);

        // reconfigure
        this.reconfigure();

        // keyMap
        if(this.shortcutKey && this.shortcutModifier) {
            var shortcutEl = this.grid.getEl();
            var shortcutCfg = [{
                 key:this.shortcutKey
                ,scope:this
                ,stopEvent:true
                ,fn:function() {
                    this.field.focus();
                }
            }];
            shortcutCfg[0][this.shortcutModifier] = true;
            this.keymap = new Ext.KeyMap(shortcutEl, shortcutCfg);
        }
    } // eo function onRender
    // }}}
    // {{{
    /**
	 * private Clear Trigger click handler
	 */
    ,onTriggerClear:function() {
        this.field.setValue('');
        this.field.focus();
        this.onTriggerSearch();
		// //////////////////////////////////
		// HACK POR DIEGO MENDEZ 16-10-2008
		// //////////////////////////////////
       // alert('todo');
     
        
             // Limpia todos los filtros de fechas
		
		if (Ext.getCmp('elGridTabTransaccionesConsultas')!=undefined){
				Ext.getCmp('elGridTabTransaccionesConsultas').store.baseParams['fec1_fc'] = '';
       
				Ext.getCmp('elGridTabTransaccionesConsultas').store.baseParams['fec2_fc'] = '';
	
				
		}
		if (Ext.getCmp('elGridTabReclamosClientes')!=undefined){
				Ext.getCmp('elGridTabReclamosClientes').store.baseParams['fec1_fr'] = '';
       
				Ext.getCmp('elGridTabReclamosClientes').store.baseParams['fec2_fr'] = '';
	
				
		}
		if (Ext.getCmp('elGridTabTransaccionesPagos')!=undefined){
				Ext.getCmp('elGridTabTransaccionesPagos').store.baseParams['fec1_fp'] = '';
       
				Ext.getCmp('elGridTabTransaccionesPagos').store.baseParams['fec2_fp'] = '';
	
				
		}
		if (Ext.getCmp('elGridTabReportesPagos')!=undefined){
				Ext.getCmp('elGridTabReportesPagos').store.baseParams['fec1_fp'] = '';
       
				Ext.getCmp('elGridTabReportesPagos').store.baseParams['fec2_fp'] = '';
				
				Ext.getCmp('elGridTabReportesPagos').store.baseParams['todo_fecha'] = '';
				
				Ext.getCmp('elGridTabReportesPagos').store.baseParams['todo_canales'] = '';
				
				Ext.getCmp('elGridTabReportesPagos').store.baseParams['id_tipo_canal'] = '';
	
				
		}
		if (Ext.getCmp('elGridTabReportesLogUsuarios')!=undefined){
				Ext.getCmp('elGridTabReportesLogUsuarios').store.baseParams['fec1_fa'] = '';
       
				Ext.getCmp('elGridTabReportesLogUsuarios').store.baseParams['fec2_fa'] = '';
				
				
				
		}
		if (Ext.getCmp('elGridTabReportesMensajesSms')!=undefined){
				Ext.getCmp('elGridTabReportesMensajesSms').store.baseParams['fec1_fa'] = '';
       
				Ext.getCmp('elGridTabReportesMensajesSms').store.baseParams['fec2_fa'] = '';
				
				
				
		}
	
        /* Recarga el grid de transacciones-consultas */
        if (Ext.getCmp('elGridTabTransaccionesConsultas')!=undefined)
        	Ext.getCmp('elGridTabTransaccionesConsultas').store.reload();
       
        /* Recarga el grid de transacciones-pagos */
        if (Ext.getCmp('elGridTabTransaccionesPagos')!=undefined)
        	Ext.getCmp('elGridTabTransaccionesPagos').store.reload();
        
      
        
        
    } // eo function onTriggerClear
    // }}}
    // {{{
    /**
	 * private Search Trigger click handler (executes the search, local or
	 * remote)
	 */
    ,onTriggerSearch:function() {
        if(!this.field.isValid()) {
            return;
        }
        var val = this.field.getValue();
        var store = this.grid.store;

        // grid's store filter
        if('local' === this.mode) {
            store.clearFilter();
            if(val) {
                store.filterBy(function(r) {
                    var retval = false;
                    this.menu.items.each(function(item) {
                        if(!item.checked || retval) {
                            return;
                        }
                        var rv = r.get(item.dataIndex);
                        rv = rv instanceof Date ? rv.format(this.dateFormat || r.fields.get(item.dataIndex).dateFormat) : rv;
                        var re = new RegExp(val, 'gi');
                        retval = re.test(rv);
                    }, this);
                    if(retval) {
                        return true;
                    }
                    return retval;
                }, this);
            }
            else {
            }
        }
        // ask server to filter records
        else {
            // clear start (necessary if we have paging)
            if(store.lastOptions && store.lastOptions.params) {
                store.lastOptions.params[store.paramNames.start] = 0;
            }

            // get fields to search array
            var fields = [];
            this.menu.items.each(function(item) {
                if(item.checked) {
                    fields.push(item.dataIndex);
                }
            });

            // add fields and query to baseParams of store
            delete(store.baseParams[this.paramNames.fields]);
            delete(store.baseParams[this.paramNames.query]);
            if (store.lastOptions && store.lastOptions.params) {
                delete(store.lastOptions.params[this.paramNames.fields]);
                delete(store.lastOptions.params[this.paramNames.query]);
            }
            if(fields.length) {
                store.baseParams[this.paramNames.fields] = Ext.encode(fields);
                store.baseParams[this.paramNames.query] = val;
            }

            // reload store
            store.reload();
        }

    } // eo function onTriggerSearch
    // }}}
    // {{{
    /**
	 * @param {Boolean}
	 *            true to disable search (TwinTriggerField), false to enable
	 */
    ,setDisabled:function() {
        this.field.setDisabled.apply(this.field, arguments);
    } // eo function setDisabled
    // }}}
    // {{{
    /**
	 * Enable search (TwinTriggerField)
	 */
    ,enable:function() {
        this.setDisabled(false);
    } // eo function enable
    // }}}
    // {{{
    /**
	 * Enable search (TwinTriggerField)
	 */
    ,disable:function() {
        this.setDisabled(true);
    } // eo function disable
    // }}}
    // {{{
    /**
	 * private (re)configures the plugin, creates menu items from column model
	 */
    ,reconfigure:function() {

        // {{{
        // remove old items
        var menu = this.menu;
        menu.removeAll();

        // add Select All item plus separator
        if(this.showSelectAll) {
            menu.add(new Ext.menu.CheckItem({
                 text:this.selectAllText
                ,checked:!(this.checkIndexes instanceof Array)
                ,hideOnClick:false
                ,handler:function(item) {
                    var checked = ! item.checked;
                    item.parentMenu.items.each(function(i) {
                        if(item !== i && i.setChecked) {
                        	
                            i.setChecked(checked);
                        }
                    });
                }
            }),'-');
        }

        // }}}
        // {{{
        // add new items
        var cm = this.grid.colModel;
        Ext.each(cm.config, function(config) {
            var disable = false;
            if(config.header && config.dataIndex) {
                Ext.each(this.disableIndexes, function(item) {
                    disable = disable ? disable : item === config.dataIndex;
                });
                if(!disable) {
                    menu.add(new Ext.menu.CheckItem({
                         text:config.header
                        ,hideOnClick:false
                        ,checked:'all' === this.checkIndexes
                        ,dataIndex:config.dataIndex
                    }));
                }
            }
        }, this);
        // }}}
        // {{{
        // check items
        if(this.checkIndexes instanceof Array) {
            Ext.each(this.checkIndexes, function(di) {
                var item = menu.items.find(function(itm) {
                    return itm.dataIndex === di;
                });
                if(item) {
                	
                    item.setChecked(true, true);
                }
            }, this);
        }
        // }}}

    } // eo function reconfigure
    // }}}

}); // eo extend

// eof



// Array data for the grids
Ext.grid.dummyData = [
    ['3m Co',71.72,0.02,0.03,'8/1 12:00am', 'Manufacturing'],
    ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am', 'Manufacturing'],
    ['Altria Group Inc',83.81,0.28,0.34,'10/1 12:00am', 'Manufacturing'],
    ['American Express Company',52.55,0.01,0.02,'9/1 10:00am', 'Finance'],
    ['American International Group, Inc.',64.13,0.31,0.49,'9/1 11:00am', 'Services'],
    ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am', 'Services'],
    ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am', 'Manufacturing'],
    ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am', 'Services'],
    ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am', 'Finance'],
    ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am', 'Manufacturing'],
    ['Exxon Mobil Corp',68.1,-0.43,-0.64,'9/1 12:00am', 'Manufacturing'],
    ['General Electric Company',34.14,-0.08,-0.23,'9/1 12:00am', 'Manufacturing'],
    ['General Motors Corporation',30.27,1.09,3.74,'9/1 12:00am', 'Automotive'],
    ['Hewlett-Packard Co.',36.53,-0.03,-0.08,'9/1 12:00am', 'Computer'],
    ['Honeywell Intl Inc',38.77,0.05,0.13,'9/1 12:00am', 'Manufacturing'],
    ['Intel Corporation',19.88,0.31,1.58,'9/1 12:00am', 'Computer'],
    ['International Business Machines',81.41,0.44,0.54,'9/1 12:00am', 'Computer'],
    ['Johnson & Johnson',64.72,0.06,0.09,'9/1 12:00am', 'Medical'],
    ['JP Morgan & Chase & Co',45.73,0.07,0.15,'9/1 12:00am', 'Finance'],
    ['McDonald\'s Corporation',36.76,0.86,2.40,'9/1 12:00am', 'Food'],
    ['Merck & Co., Inc.',40.96,0.41,1.01,'9/1 12:00am', 'Medical'],
    ['Microsoft Corporation',25.84,0.14,0.54,'9/1 12:00am', 'Computer'],
    ['Pfizer Inc',27.96,0.4,1.45,'9/1 12:00am', 'Services', 'Medical'],
    ['The Coca-Cola Company',45.07,0.26,0.58,'9/1 12:00am', 'Food'],
    ['The Home Depot, Inc.',34.64,0.35,1.02,'9/1 12:00am', 'Retail'],
    ['The Procter & Gamble Company',61.91,0.01,0.02,'9/1 12:00am', 'Manufacturing'],
    ['United Technologies Corporation',63.26,0.55,0.88,'9/1 12:00am', 'Computer'],
    ['Verizon Communications',35.57,0.39,1.11,'9/1 12:00am', 'Services'],
    ['Wal-Mart Stores, Inc.',45.45,0.73,1.63,'9/1 12:00am', 'Retail'],
    ['Walt Disney Company (The) (Holding Company)',29.89,0.24,0.81,'9/1 12:00am', 'Services']
];
