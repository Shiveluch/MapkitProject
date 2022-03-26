package com.shiveluch.mapkitproject;

import android.os.Bundle;
import android.app.Activity;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

/**
 * В этом примере показывается карта и камера выставляется на указанную точку.
 * Не забудьте запросить необходимые разрешения.
 */
public class MainActivity extends Activity {
    /**
     * Замените "your_api_key" валидным API-ключом.
     * Ключ можно получить на сайте https://developer.tech.yandex.ru/
     */
    private final String MAPKIT_API_KEY = "2af92f2d-69f8-4074-98d5-21885394b418";
    private final Point TARGET_LOCATION = new Point(59.945933, 30.320045);

    private MapView mapView;
    private MapObjectCollection mapObjects;
    PlacemarkMapObject marker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        mapView = (MapView)findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
        mapObjects = mapView.getMap().getMapObjects().addCollection();
        marker = mapObjects.addPlacemark(TARGET_LOCATION);
        marker.setIcon(ImageProvider.fromResource(getApplicationContext(), R.drawable.marker));
        IconStyle style = new IconStyle();
        style.setScale(0.05f);
        marker.setIconStyle(style);
        marker.setUserData("data");

        marker.addTapListener((mapObject, point) -> {
            String data = marker.getUserData().toString();
            System.out.println(data);
            return false;
        });

    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
}