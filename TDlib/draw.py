import numpy as np
import matplotlib
matplotlib.use("WXAgg")
import wx
import pylab as pl
import matplotlib.patches as p
import time


def partial(fn, *cargs, **ckwargs):
    def call_fn(*fargs, **fkwargs):
        d = ckwargs.copy()
        d.update(fkwargs)
        return fn(*(cargs + fargs), **d)
    return call_fn

def create(x_min,x_max,y_min,y_max):
    create.i+=1;
    fig=pl.figure(create.i)
    ax=fig.add_subplot(111)
    ax.set_xlim(x_min,x_max);
    ax.set_ylim(y_min,y_max);
    return fig,ax;
create.i=-1;

class Update:
    colours=["#000000","#0000FF","#000000","#0000FF","#FF0000","#FF66FF","#FF0000","#FF66FF"]
    weights=["ultralight","ultralight","heavy","heavy","ultralight","ultralight","heavy","heavy"]
    def __init__(self,fig,ax):
        self.lables=None;
        self.fig=fig;
        self.ax=ax;
    def __call__(self,a):
        fig=self.fig
        ax=self.ax;
        
        if self.lables==None:
            self.lables={};
            for row in a:
                x,y,c,n,r=row[0:5]
                string="X"
                if n<100000:
                    string=str(n)
                rstring="X"
                if r<100000:
                    rstring=str(r)
                string=string+" "+rstring;
                self.lables[(x,y)]=ax.text(x,y,string,color=self.colours[c],weight=self.weights[c],va="center",ha="center")

            fig.canvas.draw_idle()
        else:
            for row in a:
                x,y,c,n,r=row[0:5]
                string="X"
                if n<100000:
                    string=str(n)
                rstring="X"
                if r<100000:
                    rstring=str(r)
                string=string+" "+rstring;
                if self.lables[(x,y)].get_text()!=string:
                    self.lables[(x,y)].set_text(string);
                if self.lables[(x,y)].get_color()!=self.colours[c]:
                    self.lables[(x,y)].set_color(self.colours[c]);
                if self.lables[(x,y)].get_weight()!=self.weights[c]:
                    self.lables[(x,y)].set_weight(self.weights[c]);

            fig.canvas.draw_idle()
    


class MasterUpdate:
    def __init__(self,basename,index,create):
        self.basename=basename;
        self.index=index;
        self.create=create;
        self.updates={}
        self.playing=False;
        self.speed=1;
        self.next=0;
        self.draw()
    def __call__(self,evt):
        if isinstance(evt,wx.IdleEvent):
            if not (self.playing and time.time()>self.next):
                evt.RequestMore()
            else:
                self.next+=self.speed;
                self.index+=1;
                self.draw();
        elif isinstance(evt,wx.KeyEvent):
            if evt.GetUniChar()==32:
                self.playing=not self.playing;
                self.next=time.time();
            elif evt.GetUniChar()==189:
                self.speed*=1.1
                print "Speed ",self.speed
            elif evt.GetUniChar()==187:
                self.speed/=1.1
                print "Speed ",self.speed
            elif evt.GetUniChar()==190:
                self.index+=1;
                self.draw();
            elif evt.GetUniChar()==188:
                self.index-=1;
                self.draw();
            else:
                print evt.GetUniChar()
                evt.Skip();
                return;
        
    def draw(self):
        f=open(self.basename+"_"+str(self.index)+".tdd");
        key=f.readline()[0];
        a=np.loadtxt(f,dtype=np.int32)
        try:
            self.updates[key](a);
        except KeyError:
            self.updates[key]=Update(*self.create());
            self.updates[key](a);
        print self.index



def main():
    ##args are path,start_index,x_max,x_min,y_max,y_min
    import sys;
    if len(sys.argv)>=2:
        path=sys.argv[1]
        if len(sys.argv)>=3:
            x_max=float(sys.argv[2]);
        else:
            index=0;
        if len(sys.argv)>=4:
            x_max=float(sys.argv[3]);
        else:
            x_max=9;
        if len(sys.argv)>=5:
            x_min=float(sys.argv[4]);
        else:
            x_min=-1;
        if len(sys.argv)>=6:
            y_max=float(sys.argv[5]);
        else:
            y_max=x_max;
        if len(sys.argv)>=7:
            y_min=float(sys.argv[6]);
        else:
            y_min=x_min;
    else:
        import os;
        import re;
        pattern=re.compile("^(.*)_(\d+).tdd$")
        paths=[match.group(1) for match in (pattern.match(path) for path in os.listdir(".")) if not match==None]
        paths=dict(zip(paths,paths)).keys()
        paths.sort();
        paths=dict(zip(range(len(paths)),paths))
        print "enter the base path to process or select one of the below"
        for i,p in paths.iteritems():
            print i,p
        path=raw_input(": ");
        try:
            path=int(path)
            path=paths[path]
        except ValueError:
            pass
        except KeyError:
            pass
        index=raw_input("enter the starting index to plot from or leave blank for '0': ")
        try:
            index=int(index);
        except ValueError:
            index=0;
        x_max=raw_input("enter the maximum x for the graphs or leave blank for '9': ")
        try:
            x_max=float(x_max);
        except ValueError:
            x_max=9;
        x_min=raw_input("enter the minimum x for the graphs or leave blank for '-1': ")
        try:
            x_min=float(x_min);
        except ValueError:
            x_min=-1;
        y_max=raw_input("enter the maximum x for the graphs or leave blank for '"+str(x_max)+"': ")
        try:
            y_max=float(y_max);
        except ValueError:
            y_max=x_max;
        y_min=raw_input("enter the minimum x for the graphs or leave blank for '"+str(x_min)+"': ")
        try:
            y_min=float(y_min);
        except ValueError:
            y_min=x_min;
        

    pl.ion()

    master=MasterUpdate(path,index,partial(create,x_min,x_max,y_min,y_max));

    wx.EVT_IDLE(wx.GetApp(), master)
    wx.EVT_KEY_DOWN(wx.GetApp(), master)
    pl.show()

if __name__=="__main__":
    main();