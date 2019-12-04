package elitedj.me.todo.datastatic;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import elitedj.me.todo.R;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

public class DataStaticActivity extends AppCompatActivity {

    private LineChartView lineChart;
    private ColumnChartView barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_static);

        lineChartInit();
        barChartInit();

    }

    public void lineChartInit() {
        lineChart = findViewById(R.id.linechart);

        List<PointValue> values = new ArrayList<>();
        values.add(new PointValue(1, 4));
        values.add(new PointValue(2, 3));
        values.add(new PointValue(3, 4));
        values.add(new PointValue(4, 1));
        values.add(new PointValue(5, 1));
        values.add(new PointValue(6, 9));

        Line line = new Line(values).setColor(getResources().getColor(R.color.blue)).setCubic(true).setHasLabels(true).setStrokeWidth(2);
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);
        Axis axisX = new Axis().setHasLines(true);
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("日期");
        axisY.setName("使用小时(h)");

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        lineChart.setLineChartData(data);
        lineChart.setZoomEnabled(false);
        lineChart.startDataAnimation();
    }

    public void barChartInit() {
        barChart = findViewById(R.id.barChart);

        List<SubcolumnValue> subcolumnValues = new ArrayList<>();
        subcolumnValues.add(new SubcolumnValue(1, getResources().getColor(R.color.blue)));
        subcolumnValues.add(new SubcolumnValue(5, getResources().getColor(R.color.blue)));
        subcolumnValues.add(new SubcolumnValue(2, getResources().getColor(R.color.blue)));
        subcolumnValues.add(new SubcolumnValue(9, getResources().getColor(R.color.blue)));
        subcolumnValues.add(new SubcolumnValue(11, getResources().getColor(R.color.blue)));
        subcolumnValues.add(new SubcolumnValue(5, getResources().getColor(R.color.blue)));

        List<Column> columns = new ArrayList<>();
        columns.add(new Column(subcolumnValues));
        ColumnChartData data = new ColumnChartData();
        data.setColumns(columns);

        ArrayList<AxisValue> xValues = new ArrayList<>();
        for(int i=1;i<=12;i++) {
            xValues.add(new AxisValue(i));
        }

        Axis axisX = new Axis().setHasLines(true).setValues(xValues);
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("月份");
        axisY.setName("使用天数");

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        barChart.setColumnChartData(data);
        barChart.setZoomEnabled(false);
        barChart.startDataAnimation();
    }

}
